module Common {
//    requires spring.context;
    opens dk.sdu.mmmi.cbse.common.services;
    opens dk.sdu.mmmi.cbse.common.services.entityproperties;
    exports dk.sdu.mmmi.cbse.common.data;
    exports dk.sdu.mmmi.cbse.common.services;
    exports dk.sdu.mmmi.cbse.common.services.entityproperties;
    exports dk.sdu.mmmi.cbse.common.services.processing;
    opens dk.sdu.mmmi.cbse.common.services.processing;
}