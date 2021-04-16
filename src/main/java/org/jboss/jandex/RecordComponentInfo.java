/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2021 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.jandex;

import java.util.Arrays;

/**
 * Represents an individual Java record component that was annotated.
 *
 * <p>
 * <b>Thread-Safety</b>
 * </p>
 * This class is immutable and can be shared between threads without safe publication.
 *
 */
public final class RecordComponentInfo implements AnnotationTarget {
    private final ClassInfo clazz;
    private final byte[] name;
    private final Type type;

    RecordComponentInfo(ClassInfo clazz, byte[] name, Type type) {
        this.clazz = clazz;
        this.name = name;
        this.type = type;
    }

    /**
     * Constructs a new mock record component info
     *
     * @param method the method containing this parameter.
     * @param parameter the zero based index of this parameter
     * @return the new mock parameter info
     */
    public static RecordComponentInfo create(ClassInfo clazz, byte[] name, Type type) {
        return new RecordComponentInfo(clazz, name, type);
    }

    public final ClassInfo declaringClass() {
        return clazz;
    }

    /**
     * Returns the field this component belongs to.
     *
     * @return the declaring Java field
     */
    public final FieldInfo field() {
        return clazz.field(name);
    }

    /**
     * Returns the name of this component.
     *
     * @return the name of this component.
     */
    public final String name() {
        return Utils.fromUTF8(name);
    }

    final byte[] nameBytes() {
        return name;
    }

    public Type type() {
        return type;
    }

    /**
     * Returns a string representation describing this record component
     *
     * @return a string representation of this record component
     */
    public String toString() {
        return name();
    }

    @Override
    public final ClassInfo asClass() {
        throw new IllegalArgumentException("Not a class");
    }

    @Override
    public final FieldInfo asField() {
        throw new IllegalArgumentException("Not a field");
    }

    @Override
    public final MethodInfo asMethod() {
        throw new IllegalArgumentException("Not a method");
    }

    @Override
    public final MethodParameterInfo asMethodParameter() {
        throw new IllegalArgumentException("Not a method parameter");
    }

    @Override
    public final TypeTarget asType() {
        throw new IllegalArgumentException("Not a type");
    }

    @Override
    public RecordComponentInfo asRecordComponent() {
        return this;
    }

    @Override
    public Kind kind() {
        return Kind.RECORD_COMPONENT;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(name);
        result = 31 * result + clazz.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecordComponentInfo other = (RecordComponentInfo) o;
        return clazz.equals(other.clazz) && Arrays.equals(name, other.name);
    }

}
