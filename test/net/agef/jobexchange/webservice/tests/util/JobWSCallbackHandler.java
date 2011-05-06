
/**
 * JobWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package net.agef.jobexchange.webservice.tests.util;

    /**
     *  JobWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class JobWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public JobWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public JobWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for checkIfJobOffersExist method
            * override this method for handling normal response from checkIfJobOffersExist operation
            */
           public void receiveResultcheckIfJobOffersExist(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.CheckIfJobOffersExistResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkIfJobOffersExist operation
           */
            public void receiveErrorcheckIfJobOffersExist(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkIfUserIsJobOfferOwner method
            * override this method for handling normal response from checkIfUserIsJobOfferOwner operation
            */
           public void receiveResultcheckIfUserIsJobOfferOwner(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.CheckIfUserIsJobOfferOwnerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkIfUserIsJobOfferOwner operation
           */
            public void receiveErrorcheckIfUserIsJobOfferOwner(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOfferSearchResultsByCriteria method
            * override this method for handling normal response from getJobOfferSearchResultsByCriteria operation
            */
           public void receiveResultgetJobOfferSearchResultsByCriteria(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferSearchResultsByCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOfferSearchResultsByCriteria operation
           */
            public void receiveErrorgetJobOfferSearchResultsByCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllJobOffers method
            * override this method for handling normal response from getAllJobOffers operation
            */
           public void receiveResultgetAllJobOffers(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetAllJobOffersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllJobOffers operation
           */
            public void receiveErrorgetAllJobOffers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for increaseJobApplicationLinkCounter method
            * override this method for handling normal response from increaseJobApplicationLinkCounter operation
            */
           public void receiveResultincreaseJobApplicationLinkCounter(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.IncreaseJobApplicationLinkCounterResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from increaseJobApplicationLinkCounter operation
           */
            public void receiveErrorincreaseJobApplicationLinkCounter(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyJobOffer method
            * override this method for handling normal response from modifyJobOffer operation
            */
           public void receiveResultmodifyJobOffer(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.ModifyJobOfferResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyJobOffer operation
           */
            public void receiveErrormodifyJobOffer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOffersByUserAndCriteria method
            * override this method for handling normal response from getJobOffersByUserAndCriteria operation
            */
           public void receiveResultgetJobOffersByUserAndCriteria(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOffersByUserAndCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOffersByUserAndCriteria operation
           */
            public void receiveErrorgetJobOffersByUserAndCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOffersByUser method
            * override this method for handling normal response from getJobOffersByUser operation
            */
           public void receiveResultgetJobOffersByUser(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOffersByUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOffersByUser operation
           */
            public void receiveErrorgetJobOffersByUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOfferDetails method
            * override this method for handling normal response from getJobOfferDetails operation
            */
           public void receiveResultgetJobOfferDetails(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferDetailsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOfferDetails operation
           */
            public void receiveErrorgetJobOfferDetails(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setJobOfferOnlineState method
            * override this method for handling normal response from setJobOfferOnlineState operation
            */
           public void receiveResultsetJobOfferOnlineState(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.SetJobOfferOnlineStateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setJobOfferOnlineState operation
           */
            public void receiveErrorsetJobOfferOnlineState(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOfferByCriteria method
            * override this method for handling normal response from getJobOfferByCriteria operation
            */
           public void receiveResultgetJobOfferByCriteria(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferByCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOfferByCriteria operation
           */
            public void receiveErrorgetJobOfferByCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getReceivedJobOfferApplications method
            * override this method for handling normal response from getReceivedJobOfferApplications operation
            */
           public void receiveResultgetReceivedJobOfferApplications(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetReceivedJobOfferApplicationsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getReceivedJobOfferApplications operation
           */
            public void receiveErrorgetReceivedJobOfferApplications(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkIfJobexchangeIsOnline method
            * override this method for handling normal response from checkIfJobexchangeIsOnline operation
            */
           public void receiveResultcheckIfJobexchangeIsOnline(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.CheckIfJobexchangeIsOnlineResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkIfJobexchangeIsOnline operation
           */
            public void receiveErrorcheckIfJobexchangeIsOnline(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for applyToJobOffer method
            * override this method for handling normal response from applyToJobOffer operation
            */
           public void receiveResultapplyToJobOffer(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.ApplyToJobOfferResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from applyToJobOffer operation
           */
            public void receiveErrorapplyToJobOffer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addJobOffer method
            * override this method for handling normal response from addJobOffer operation
            */
           public void receiveResultaddJobOffer(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.AddJobOfferResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addJobOffer operation
           */
            public void receiveErroraddJobOffer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOfferOnlineState method
            * override this method for handling normal response from getJobOfferOnlineState operation
            */
           public void receiveResultgetJobOfferOnlineState(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferOnlineStateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOfferOnlineState operation
           */
            public void receiveErrorgetJobOfferOnlineState(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getJobOfferSearchResultsAmountByCriteria method
            * override this method for handling normal response from getJobOfferSearchResultsAmountByCriteria operation
            */
           public void receiveResultgetJobOfferSearchResultsAmountByCriteria(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferSearchResultsAmountByCriteriaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getJobOfferSearchResultsAmountByCriteria operation
           */
            public void receiveErrorgetJobOfferSearchResultsAmountByCriteria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteJobOffer method
            * override this method for handling normal response from deleteJobOffer operation
            */
           public void receiveResultdeleteJobOffer(
                    net.agef.jobexchange.webservice.tests.util.JobWSStub.DeleteJobOfferResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteJobOffer operation
           */
            public void receiveErrordeleteJobOffer(java.lang.Exception e) {
            }
                


    }
    