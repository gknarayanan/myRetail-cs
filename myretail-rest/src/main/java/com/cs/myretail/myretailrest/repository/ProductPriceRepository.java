package com.cs.myretail.myretailrest.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cs.myretail.myretailrest.model.ProductPrice;

@Repository
public interface ProductPriceRepository extends CassandraRepository<ProductPrice, Long> {

}
