package com.hcl.sprbootdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.sprbootdemo.entity.Payments;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long>{

}
