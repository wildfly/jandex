/*
 * JBoss, Home of Professional Open Source. Copyright 2021 Red Hat, Inc., and
 * individual contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.jboss.jandex.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.IndexReader;
import org.jboss.jandex.IndexWriter;
import org.jboss.jandex.Indexer;
import org.junit.Before;
import org.junit.Test;

public class RecordTestCase {

    Index index;

    @Before
    public void setup() throws IOException {
        index = buildIndex();
    }

    @Test
    public void testRecordHasAnnotation() throws IOException {
        ClassInfo rec = index.getClassByName(DotName.createSimple("test.RecordExample"));
        AnnotationInstance anno = rec.classAnnotation(DotName.createSimple("test.RecordExample$RecordAnnotation"));
        assertNotNull(anno);
        assertEquals("Example", anno.value().asString());
    }

    @Test
    public void testRecordComponentHasAnnotation() throws IOException {
        ClassInfo rec = index.getClassByName(DotName.createSimple("test.RecordExample"));
        List<AnnotationInstance> componentAnnos = rec.annotations().get(DotName.createSimple("test.RecordExample$ComponentAnnotation"));
        assertNotNull(componentAnnos);
        assertEquals(1, componentAnnos.size());
        assertEquals(AnnotationTarget.Kind.RECORD_COMPONENT, componentAnnos.get(0).target().kind());
        assertEquals("name", componentAnnos.get(0).target().asRecordComponent().name());
        assertEquals("nameComponent", componentAnnos.get(0).value().asString());
    }

    private Index buildIndex() throws IOException {
        Indexer indexer = new Indexer();
        indexer.index(getClass().getClassLoader().getResourceAsStream("test/RecordExample.class"));
        indexer.index(getClass().getClassLoader().getResourceAsStream("test/RecordExample$NestedEmptyRecord.class"));
        indexer.index(getClass().getClassLoader().getResourceAsStream("test/RecordExample$RecordAnnotation.class"));

        Index index = indexer.complete();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new IndexWriter(baos).write(index);

        index = new IndexReader(new ByteArrayInputStream(baos.toByteArray())).read();

        return index;
    }

}
