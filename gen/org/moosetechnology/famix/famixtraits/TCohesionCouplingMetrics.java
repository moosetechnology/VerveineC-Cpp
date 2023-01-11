// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TCohesionCouplingMetrics")
public interface TCohesionCouplingMetrics extends TEntityMetaLevelDependency, TNamedEntity, TPackage, TSourceEntity {

        @FameProperty(name = "efferentCoupling", derived = true)
    public Number getEfferentCoupling();

    @FameProperty(name = "afferentCoupling", derived = true)
    public Number getAfferentCoupling();

    @FameProperty(name = "distance", derived = true)
    public Number getDistance();

    @FameProperty(name = "instability", derived = true)
    public Number getInstability();

    @FameProperty(name = "abstractness", derived = true)
    public Number getAbstractness();

    @FameProperty(name = "martinCohesion", derived = true)
    public Number getMartinCohesion();



}
