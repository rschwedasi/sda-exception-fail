package de.signaliduna.blueprint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class Payload {

  @NotNull
  @JsonProperty
  String someString;

  public String getSomeString() {
    return someString;
  }

  public void setSomeString(String someString) {
    this.someString = someString;
  }

}
