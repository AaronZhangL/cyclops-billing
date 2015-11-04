package ch.icclab.cyclops.application;

import ch.icclab.cyclops.resource.impl.BillResource;
import ch.icclab.cyclops.resource.impl.RootResource;
import ch.icclab.cyclops.util.APICallCounter;
import ch.icclab.cyclops.util.APICallEndpoint;
import ch.icclab.cyclops.util.Load;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Author: Srikanta
 * Created on: 26-May-15
 * Description: The application class which acts as a router to service the incoming API request
 */
public class BillingServiceApplication extends Application {
    /**
     * This method handles the incoming request and routes it to the appropriate resource class
     * <p>
     * Pseudo code
     * 1. Create an instance of Router
     * 2. Attach the api end points and their respective resource class for request handling
     * 3. Return the router
     *
     * @return Restlet
     */
    public Restlet createInboundRoot() {
        loadConfiguration(getContext());
        APICallCounter counter = APICallCounter.getInstance();

        Router router = new Router();
        router.attach("/", RootResource.class);
        counter.registerEndpoint("/");

        router.attach("/status", APICallEndpoint.class);
        counter.registerEndpoint("/status");

        router.attach("/invoice", BillResource.class);
        counter.registerEndpoint("/invoice");
        return router;
    }

    /**
     * Loads the configuration file at the beginning of the application startup
     * <p>
     * Pseudo Code
     * 1. Create the LoadConfiguration class
     * 2. Load the file if the the existing instance of the class is empty
     *
     * @param context
     */
    private void loadConfiguration(Context context) {
        Load load = new Load();
        if (load.configuration == null) {
            load.configFile(getContext());
        }
    }
}
