package dev.matheuspereira.fluxcred.domain.ports.driver;

public interface IBaseService<T> {

  /**
   * Create new model.
   *
   * @param model data of model to create.
   * @return the model register.
   */
  T create(T model);

  /**
   * Get one model by ID.
   *
   * @param id the modelID.
   * @return the model fined or null when not found.
   */
  T getById(Integer id);

  /**
   * Update the model.
   *
   * @param id    the modelID.
   * @param model data of model to update.
   * @return the model updated.
   */
  T update(Integer id, T model);

  /**
   * Delete one model by ID.
   *
   * @param id the modelID.
   */
  void delete(Integer id);
}
