package domain.interfaces;

public interface RepositoryCrud<T> {
    T findById(Integer id);
    void save(T t);
    void update(Integer id, T t);
}
