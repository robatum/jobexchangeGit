
/**
 * ApplicantWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package net.agef.jobexchange.webservice.tests.util;

    /**
     *  ApplicantWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ApplicantWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ApplicantWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ApplicantWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for setApplicantProfileOnlineState method
            * override this method for handling normal response from setApplicantProfileOnlineState operation
            */
           public void receiveResultsetApplicantProfileOnlineState(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.SetApplicantProfileOnlineStateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setApplicantProfileOnlineState operation
           */
            public void receiveErrorsetApplicantProfileOnlineState(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyApplicantProfile method
            * override this method for handling normal response from modifyApplicantProfile operation
            */
           public void receiveResultmodifyApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.ModifyApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyApplicantProfile operation
           */
            public void receiveErrormodifyApplicantProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantProfileOnlineState method
            * override this method for handling normal response from getApplicantProfileOnlineState operation
            */
           public void receiveResultgetApplicantProfileOnlineState(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileOnlineStateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantProfileOnlineState operation
           */
            public void receiveErrorgetApplicantProfileOnlineState(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantsSearchResultsAmountByExtendedCriteria method
            * override this method for handling normal response from getApplicantsSearchResultsAmountByExtendedCriteria operation
            */
           public void receiveResultgetApplicantsSearchResultsAmountByExtendedCriteria(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsSearchResultsAmountByExtendedCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantsSearchResultsAmountByExtendedCriteria operation
           */
            public void receiveErrorgetApplicantsSearchResultsAmountByExtendedCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllApplicants method
            * override this method for handling normal response from getAllApplicants operation
            */
           public void receiveResultgetAllApplicants(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetAllApplicantsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllApplicants operation
           */
            public void receiveErrorgetAllApplicants(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantsByCriteria method
            * override this method for handling normal response from getApplicantsByCriteria operation
            */
           public void receiveResultgetApplicantsByCriteria(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsByCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantsByCriteria operation
           */
            public void receiveErrorgetApplicantsByCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantProfileByUserId method
            * override this method for handling normal response from getApplicantProfileByUserId operation
            */
           public void receiveResultgetApplicantProfileByUserId(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileByUserIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantProfileByUserId operation
           */
            public void receiveErrorgetApplicantProfileByUserId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantProfileOnlineStateByApplicantProfileId method
            * override this method for handling normal response from getApplicantProfileOnlineStateByApplicantProfileId operation
            */
           public void receiveResultgetApplicantProfileOnlineStateByApplicantProfileId(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileOnlineStateByApplicantProfileIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantProfileOnlineStateByApplicantProfileId operation
           */
            public void receiveErrorgetApplicantProfileOnlineStateByApplicantProfileId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantsSearchResultsAmountByCriteria method
            * override this method for handling normal response from getApplicantsSearchResultsAmountByCriteria operation
            */
           public void receiveResultgetApplicantsSearchResultsAmountByCriteria(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsSearchResultsAmountByCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantsSearchResultsAmountByCriteria operation
           */
            public void receiveErrorgetApplicantsSearchResultsAmountByCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkForApplicantProfile method
            * override this method for handling normal response from checkForApplicantProfile operation
            */
           public void receiveResultcheckForApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.CheckForApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkForApplicantProfile operation
           */
            public void receiveErrorcheckForApplicantProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantsSearchResultsByCriteria method
            * override this method for handling normal response from getApplicantsSearchResultsByCriteria operation
            */
           public void receiveResultgetApplicantsSearchResultsByCriteria(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsSearchResultsByCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantsSearchResultsByCriteria operation
           */
            public void receiveErrorgetApplicantsSearchResultsByCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantProfile method
            * override this method for handling normal response from getApplicantProfile operation
            */
           public void receiveResultgetApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantProfile operation
           */
            public void receiveErrorgetApplicantProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addApplicantProfile method
            * override this method for handling normal response from addApplicantProfile operation
            */
           public void receiveResultaddApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.AddApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addApplicantProfile operation
           */
            public void receiveErroraddApplicantProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getApplicantsByExtendedCriteria method
            * override this method for handling normal response from getApplicantsByExtendedCriteria operation
            */
           public void receiveResultgetApplicantsByExtendedCriteria(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsByExtendedCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApplicantsByExtendedCriteria operation
           */
            public void receiveErrorgetApplicantsByExtendedCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteApplicantProfile method
            * override this method for handling normal response from deleteApplicantProfile operation
            */
           public void receiveResultdeleteApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.DeleteApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteApplicantProfile operation
           */
            public void receiveErrordeleteApplicantProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteInwentApplicantProfile method
            * override this method for handling normal response from deleteInwentApplicantProfile operation
            */
           public void receiveResultdeleteInwentApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.DeleteInwentApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteInwentApplicantProfile operation
           */
            public void receiveErrordeleteInwentApplicantProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addInwentApplicantProfile method
            * override this method for handling normal response from addInwentApplicantProfile operation
            */
           public void receiveResultaddInwentApplicantProfile(
                    net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.AddInwentApplicantProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addInwentApplicantProfile operation
           */
            public void receiveErroraddInwentApplicantProfile(java.lang.Exception e) {
            }
                


    }
    