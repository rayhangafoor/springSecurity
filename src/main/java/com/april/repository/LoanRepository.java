package com.april.repository;

import java.util.List;

import com.april.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {

	@PreAuthorize("hasRole('ROOT')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
