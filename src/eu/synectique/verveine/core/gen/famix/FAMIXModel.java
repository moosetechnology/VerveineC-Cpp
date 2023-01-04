// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.MetaRepository;

public class FAMIXModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(eu.synectique.verveine.core.gen.famix.Invocation.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.JavaSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.NamespaceGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.SmalltalkMonticelloSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Exception.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.UnknownContainerEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.DeclaredException.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ContainerEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.MultipleFileAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ParameterizedType.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AbstractFileAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.PreprocessorStatement.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.SourceAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.CFile.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Inheritance.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AnnotationInstance.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ParameterizableClass.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.TypeAlias.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Include.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AnnotationInstanceAttribute.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Comment.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.PreprocessorDefine.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ClassGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.DereferencedInvocation.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.LeafEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ScopingEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.SmalltalkSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Template.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.LocalVariable.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.UnknownVariable.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ImplicitVariable.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Entity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.SourcedEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.GlobalVariableGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.GlobalVariable.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.TypeGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.UnknownBehaviouralEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.NamedEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AnnotationTypeGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Attribute.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Reference.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.BehaviouralEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.IndexedFileAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.CaughtException.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.MethodGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Header.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Module.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Function.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.CppSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.SourceTextAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Enum.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AnnotationTypeAttribute.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.BehaviouralReference.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.CSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.PreprocessorIfdef.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.PharoAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Parameter.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ParameterType.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Access.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Class.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.PrimitiveType.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AnnotationInstanceGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.EnumValue.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Type.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.ThrownException.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Namespace.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.UnknownSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.CustomSourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.FileAnchor.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.AnnotationType.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Method.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.StructuralEntity.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.PackageGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.CompilationUnit.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.SourceLanguage.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Package.class);
		metamodel.with(eu.synectique.verveine.core.gen.famix.Association.class);

    }

}

