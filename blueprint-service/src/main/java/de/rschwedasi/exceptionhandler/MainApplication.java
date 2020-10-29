package de.rschwedasi.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rschwedasi.exceptionhandler.api.ExampleApi;
import de.rschwedasi.exceptionhandler.config.MainConfiguration;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.enterprise.context.ApplicationScoped;
import org.sdase.commons.server.jackson.JacksonConfigurationBundle;
import org.sdase.commons.server.prometheus.PrometheusBundle;
import org.sdase.commons.server.swagger.SwaggerBundle;
import org.sdase.commons.server.trace.TraceTokenBundle;
import org.sdase.commons.server.weld.DropwizardWeldHelper;
import org.sdase.commons.server.weld.WeldBundle;

@ApplicationScoped
public class MainApplication extends Application<MainConfiguration> {

  public static void main(String[] args) throws Exception {
    DropwizardWeldHelper.run(MainApplication.class, args);
  }

  private static void disableWadl(ServletEnvironment servlets) {
    servlets.setInitParameter("jersey.config.server.wadl.disableWadl", "true");
  }

  private static void setupObjectMapper(ObjectMapper objectMapper) {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  private static void setupResources(JerseyEnvironment jersey) {
    jersey.register(ExampleApi.class);
  }

  @Override
  public void initialize(Bootstrap<MainConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(),
        new EnvironmentVariableSubstitutor(false)));
    bootstrap.addBundle(JacksonConfigurationBundle.builder().build());
    bootstrap.addBundle(PrometheusBundle.builder().build());
    bootstrap.addBundle(new WeldBundle());
    bootstrap.addBundle(TraceTokenBundle.builder().build());
    bootstrap.addBundle(SwaggerBundle.builder()
        .withTitle("SDASI Blueprint Service")
        .addResourcePackageClass(getClass())
        .withVersion("1.0")
        .withDescription("SDA SI template project for all types of sda services")
        .withContact("SDA SI Core Team", "sda-si-core-team@signal-iduna.de")
        .withTermsOfServiceUrl("http://wiki.system.local/display/sdasi")
        .build());
  }

  @Override
  public void run(MainConfiguration configuration, Environment environment) {
    disableWadl(environment.servlets());
    setupObjectMapper(environment.getObjectMapper());
    setupResources(environment.jersey());
  }

}
