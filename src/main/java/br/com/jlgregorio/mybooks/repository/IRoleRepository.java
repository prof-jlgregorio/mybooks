package br.com.jlgregorio.mybooks.repository;

import br.com.jlgregorio.mybooks.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleModel, Long> {

    public RoleModel findByNameEquals(String name);

}
