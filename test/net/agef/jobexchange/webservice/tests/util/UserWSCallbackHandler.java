
/**
 * UserWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package net.agef.jobexchange.webservice.tests.util;

    /**
     *  UserWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class UserWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public UserWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public UserWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for changeUserRoleToOrganisation method
            * override this method for handling normal response from changeUserRoleToOrganisation operation
            */
           public void receiveResultchangeUserRoleToOrganisation(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.ChangeUserRoleToOrganisationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changeUserRoleToOrganisation operation
           */
            public void receiveErrorchangeUserRoleToOrganisation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addInwentAlumniUser method
            * override this method for handling normal response from addInwentAlumniUser operation
            */
           public void receiveResultaddInwentAlumniUser(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.AddInwentAlumniUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addInwentAlumniUser operation
           */
            public void receiveErroraddInwentAlumniUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyUser method
            * override this method for handling normal response from modifyUser operation
            */
           public void receiveResultmodifyUser(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.ModifyUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyUser operation
           */
            public void receiveErrormodifyUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for switchContactAddress method
            * override this method for handling normal response from switchContactAddress operation
            */
           public void receiveResultswitchContactAddress(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.SwitchContactAddressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from switchContactAddress operation
           */
            public void receiveErrorswitchContactAddress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteUser method
            * override this method for handling normal response from deleteUser operation
            */
           public void receiveResultdeleteUser(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.DeleteUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteUser operation
           */
            public void receiveErrordeleteUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addAlumniUser method
            * override this method for handling normal response from addAlumniUser operation
            */
           public void receiveResultaddAlumniUser(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.AddAlumniUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addAlumniUser operation
           */
            public void receiveErroraddAlumniUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for changeUserRoleToAlumni method
            * override this method for handling normal response from changeUserRoleToAlumni operation
            */
           public void receiveResultchangeUserRoleToAlumni(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.ChangeUserRoleToAlumniResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changeUserRoleToAlumni operation
           */
            public void receiveErrorchangeUserRoleToAlumni(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkIfUserExist method
            * override this method for handling normal response from checkIfUserExist operation
            */
           public void receiveResultcheckIfUserExist(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfUserExistResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkIfUserExist operation
           */
            public void receiveErrorcheckIfUserExist(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addOrganisationUser method
            * override this method for handling normal response from addOrganisationUser operation
            */
           public void receiveResultaddOrganisationUser(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.AddOrganisationUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addOrganisationUser operation
           */
            public void receiveErroraddOrganisationUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkIfInwentUserExist method
            * override this method for handling normal response from checkIfInwentUserExist operation
            */
           public void receiveResultcheckIfInwentUserExist(
                    net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfInwentUserExistResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkIfInwentUserExist operation
           */
            public void receiveErrorcheckIfInwentUserExist(java.lang.Exception e) {
            }
                


    }
    