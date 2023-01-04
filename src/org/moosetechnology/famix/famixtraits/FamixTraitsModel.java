// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.MetaRepository;

public class FamixTraitsModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.famixtraits.AnnotationInstanceGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.AnnotationTypeGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.ClassGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.FamixModel.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.FileGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.FolderGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.GlobalVariableGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.InvocationGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.MethodGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.NamespaceGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.PackageGroup.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAccess.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAccessible.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAnnotationInstance.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAnnotationInstanceAttribute.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAnnotationType.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAnnotationTypeAttribute.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAssociation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TAttribute.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TCanBeAbstract.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TCanBeClassSide.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TCanBeFinal.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TCanImplement.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TClass.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TClassHierarchyNavigation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TClassMetrics.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TClassWithVisibility.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TCohesionCouplingMetrics.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TComment.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TCompilationUnit.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TDefinedInModule.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TDereferencedInvocation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TEnum.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TEnumValue.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TException.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFile.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFileInclude.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFileNavigation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFileSystemEntity.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFolder.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TFunction.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TGlobalVariable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.THasImmediateSource.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.THasKind.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.THasModifiers.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.THasSignature.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.THasVisibility.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.THeader.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TImplementable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TImplementation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TImplicitVariable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TIndexedFileNavigation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TInheritance.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TInvocable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TInvocation.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TInvocationsReceiver.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TLCOMMetrics.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TLocalVariable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TMethod.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TMethodMetrics.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TModule.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TMultipleFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TNamedEntity.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TNamespace.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TPackage.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TPackageable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TParameter.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TParameterType.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TParameterizedType.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TParameterizedTypeUser.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TPreprocessorDefine.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TPreprocessorIfdef.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TPrimitiveType.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TReference.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TReferenceable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TRelativeSourceAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TSourceAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TSourceEntity.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TStructuralEntity.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTemplate.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTemplateUser.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTrait.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTraitUsage.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTraitUser.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TType.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTypeAlias.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTypedAnnotationInstance.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTypedAnnotationInstanceAttribute.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TTypedEntity.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TUnknownSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TUnknownVariable.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithAccesses.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithAnnotationInstanceAttributes.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithAnnotationInstances.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithAnnotationTypes.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithAttributes.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithClasses.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithComments.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithCompilationUnits.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithDereferencedInvocations.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithEnumValues.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithExceptions.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithFileIncludes.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithFiles.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithFunctions.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithGlobalVariables.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithHeaders.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithImplicitVariables.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithInheritances.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithInvocations.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithLocalVariables.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithMethods.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithPackages.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithParameterizedTypeUsers.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithParameterizedTypes.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithParameters.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithReferences.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithSourceLanguages.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithStatements.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithTemplates.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithTraits.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithTypeAliases.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TWithTypes.class);
		metamodel.with(org.moosetechnology.famix.famixtraits.TypeGroup.class);

    }

}

