package net.agef.jobexchange.services.internal;

import java.lang.reflect.Modifier;

import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.services.internal.GenericSelectionModel;
import net.agef.jobexchange.services.internal.GenericValueEncoder;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.util.BodyBuilder;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.TransformMethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InjectSelectionModelWorker implements ComponentClassTransformWorker {

    final private Logger _logger = LoggerFactory.getLogger(InjectSelectionModelWorker.class);
    final private PropertyAccess _access;
    
        public InjectSelectionModelWorker(PropertyAccess propertyAccess) {
                _access = propertyAccess;
        }

        public void transform(ClassTransformation transformation, MutableComponentModel componentModel) {

                for (String fieldName : transformation.findFieldsWithAnnotation(InjectSelectionModel.class)) {
                        InjectSelectionModel annotation = transformation.getFieldAnnotation(fieldName, InjectSelectionModel.class);
                        
                        if (_logger.isDebugEnabled()){
                                _logger.debug("Creating selection model getter method for the field " + fieldName);
                        }

                        String accessActualName = transformation.addField(Modifier.PRIVATE, "org.apache.tapestry5.ioc.services.PropertyAccess", "_access");
                        transformation.injectField(accessActualName, _access);
                        
                        addGetSelectionModelMethod(transformation, fieldName, annotation.labelField(), accessActualName);

                        if (_logger.isDebugEnabled()){
                                _logger.debug("Creating value encoder getter method for the field " + fieldName);
                        }

                        addGetValueEncoderMethod(transformation, fieldName, annotation.idField(), accessActualName);
                        
                }

        }

        private void addGetSelectionModelMethod(ClassTransformation transformation, String fieldName, String labelField, String accessName) {
                
                String methodName = "get" + InternalUtils.capitalize(InternalUtils.stripMemberName(fieldName)) + "SelectionModel";
                                
                String modelQualifiedName = (GenericSelectionModel.class).getName();
                TransformMethodSignature sig = 
                        new TransformMethodSignature(Modifier.PUBLIC, modelQualifiedName, methodName, null, null);

                BodyBuilder builder = new BodyBuilder();
                builder.begin();
                builder.addln("return new " + modelQualifiedName + "(" + fieldName + ", \"" + labelField +"\", " + accessName + ");");
                builder.end();

                transformation.addMethod(sig, builder.toString());

        }
        
        private void addGetValueEncoderMethod(ClassTransformation transformation, String fieldName, String idField, String accessName) {
                
                String methodName = "get" + InternalUtils.capitalize(InternalUtils.stripMemberName(fieldName)) + "ValueEncoder";
                
                String encoderQualifiedName = (GenericValueEncoder.class).getName();
                TransformMethodSignature sig = 
                        new TransformMethodSignature(Modifier.PUBLIC, encoderQualifiedName, methodName, null, null);

                BodyBuilder builder = new BodyBuilder();
                builder.begin();
                String line = "return new " + encoderQualifiedName + "(" + fieldName + ",\"" + idField +"\", " + accessName + ");";
                builder.addln(line);
                builder.end();

                transformation.addMethod(sig, builder.toString());

        }

}
