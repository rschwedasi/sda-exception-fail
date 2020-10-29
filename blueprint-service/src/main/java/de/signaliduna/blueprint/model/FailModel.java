package de.signaliduna.blueprint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class FailModel {

  @Valid
  @JsonProperty
  @NotNull
  private Nested nested;

  public Nested getNested() {
    return nested;
  }

  public void setNested(Nested nested) {
    this.nested = nested;
  }
}
