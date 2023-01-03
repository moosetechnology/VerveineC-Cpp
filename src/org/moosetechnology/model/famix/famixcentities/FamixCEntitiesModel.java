// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixcentities;

import ch.akuhn.fame.MetaRepository;

public class FamixCEntitiesModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Access.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.AliasType.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Association.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Attribute.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.BehaviouralEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.BehaviouralReference.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Comment.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.ContainerEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.DerefInvocation.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Entity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Enum.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.EnumValue.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.FamixCModel.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.FileAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Function.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.GlobalVar.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Invocation.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.LocalVar.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.MultipleFileanchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.NamedEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Parameter.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.PrimitiveType.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Reference.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.RelativeSourceAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.SourceAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.SourceLanguage.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.SourceTextAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.SourcedEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Struct.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Type.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.Union.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.UnknownBehaviour.class);
		metamodel.with(org.moosetechnology.model.famix.famixcentities.UnknownSourceLanguage.class);

    }

}

