package org.jboss.jandex;

/**
 * @author Jason T. Greene
 */
public class TypeParameterTypeTarget extends PositionBasedTypeTarget {

    TypeParameterTypeTarget(AnnotationTarget enclosingTarget, int position) {
        super(enclosingTarget, position);
    }

    TypeParameterTypeTarget(AnnotationTarget enclosingTarget, Type target, int position) {
        super(enclosingTarget, target, position);
    }

    @Override
    public Usage usage() {
        return Usage.TYPE_PARAMETER;
    }

    @Override
    public TypeParameterTypeTarget asTypeParameter() {
        return this;
    }
}