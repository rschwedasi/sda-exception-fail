package de.signaliduna.blueprint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class OkModel {

  @JsonProperty
  @Valid
  @NotNull
  private Payload payload;

  public Payload getPayload() {
    return payload;
  }

  public void setPayload(Payload payload) {
    this.payload = payload;
  }
}
