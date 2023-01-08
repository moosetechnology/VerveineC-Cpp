// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.MetaRepository;

public class FamixCEntitiesModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.famixcentities.Access.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.AliasType.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Association.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Attribute.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.BehaviouralEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.BehaviouralReference.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Comment.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.ContainerEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.DereferencedInvocation.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Entity.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Enum.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.EnumValue.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.FamixCModel.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Function.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.GlobalVariable.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.IndexedFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Invocation.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.LocalVariable.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Module.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.MultipleFileAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.NamedEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Parameter.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.PrimitiveType.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Reference.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.RelativeSourceAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.SourceAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.SourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.SourceTextAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.SourcedEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Struct.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Type.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.Union.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.UnknownBehaviour.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.UnknownContainerEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.UnknownSourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.famixcentities.UnknownVariable.class);

    }

}

