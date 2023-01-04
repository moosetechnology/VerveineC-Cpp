// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.MetaRepository;

public class FAMIXModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.cpp.Invocation.class);
		metamodel.with(org.moosetechnology.famix.cpp.JavaSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.NamespaceGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.SmalltalkMonticelloSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.Exception.class);
		metamodel.with(org.moosetechnology.famix.cpp.UnknownContainerEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.DeclaredException.class);
		metamodel.with(org.moosetechnology.famix.cpp.ContainerEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.MultipleFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.ParameterizedType.class);
		metamodel.with(org.moosetechnology.famix.cpp.AbstractFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.PreprocessorStatement.class);
		metamodel.with(org.moosetechnology.famix.cpp.SourceAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.CFile.class);
		metamodel.with(org.moosetechnology.famix.cpp.Inheritance.class);
		metamodel.with(org.moosetechnology.famix.cpp.AnnotationInstance.class);
		metamodel.with(org.moosetechnology.famix.cpp.ParameterizableClass.class);
		metamodel.with(org.moosetechnology.famix.cpp.TypeAlias.class);
		metamodel.with(org.moosetechnology.famix.cpp.Include.class);
		metamodel.with(org.moosetechnology.famix.cpp.AnnotationInstanceAttribute.class);
		metamodel.with(org.moosetechnology.famix.cpp.Comment.class);
		metamodel.with(org.moosetechnology.famix.cpp.PreprocessorDefine.class);
		metamodel.with(org.moosetechnology.famix.cpp.ClassGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.DereferencedInvocation.class);
		metamodel.with(org.moosetechnology.famix.cpp.LeafEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.ScopingEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.SmalltalkSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.Template.class);
		metamodel.with(org.moosetechnology.famix.cpp.LocalVariable.class);
		metamodel.with(org.moosetechnology.famix.cpp.UnknownVariable.class);
		metamodel.with(org.moosetechnology.famix.cpp.ImplicitVariable.class);
		metamodel.with(org.moosetechnology.famix.cpp.Entity.class);
		metamodel.with(org.moosetechnology.famix.cpp.SourcedEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.GlobalVariableGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.GlobalVariable.class);
		metamodel.with(org.moosetechnology.famix.cpp.TypeGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.UnknownBehaviouralEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.NamedEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.AnnotationTypeGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.Attribute.class);
		metamodel.with(org.moosetechnology.famix.cpp.Reference.class);
		metamodel.with(org.moosetechnology.famix.cpp.BehaviouralEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.IndexedFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.CaughtException.class);
		metamodel.with(org.moosetechnology.famix.cpp.MethodGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.Header.class);
		metamodel.with(org.moosetechnology.famix.cpp.Module.class);
		metamodel.with(org.moosetechnology.famix.cpp.Function.class);
		metamodel.with(org.moosetechnology.famix.cpp.CppSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.SourceTextAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.Enum.class);
		metamodel.with(org.moosetechnology.famix.cpp.AnnotationTypeAttribute.class);
		metamodel.with(org.moosetechnology.famix.cpp.BehaviouralReference.class);
		metamodel.with(org.moosetechnology.famix.cpp.CSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.PreprocessorIfdef.class);
		metamodel.with(org.moosetechnology.famix.cpp.PharoAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.Parameter.class);
		metamodel.with(org.moosetechnology.famix.cpp.ParameterType.class);
		metamodel.with(org.moosetechnology.famix.cpp.Access.class);
		metamodel.with(org.moosetechnology.famix.cpp.Class.class);
		metamodel.with(org.moosetechnology.famix.cpp.PrimitiveType.class);
		metamodel.with(org.moosetechnology.famix.cpp.AnnotationInstanceGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.EnumValue.class);
		metamodel.with(org.moosetechnology.famix.cpp.Type.class);
		metamodel.with(org.moosetechnology.famix.cpp.ThrownException.class);
		metamodel.with(org.moosetechnology.famix.cpp.Namespace.class);
		metamodel.with(org.moosetechnology.famix.cpp.UnknownSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.CustomSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.FileAnchor.class);
		metamodel.with(org.moosetechnology.famix.cpp.AnnotationType.class);
		metamodel.with(org.moosetechnology.famix.cpp.Method.class);
		metamodel.with(org.moosetechnology.famix.cpp.StructuralEntity.class);
		metamodel.with(org.moosetechnology.famix.cpp.PackageGroup.class);
		metamodel.with(org.moosetechnology.famix.cpp.CompilationUnit.class);
		metamodel.with(org.moosetechnology.famix.cpp.SourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.cpp.Package.class);
		metamodel.with(org.moosetechnology.famix.cpp.Association.class);

    }

}

