import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="productId">Product Id</span>
          </dt>
          <dd>{productEntity.productId}</dd>
          <dt>
            <span id="productCode">Product Code</span>
          </dt>
          <dd>{productEntity.productCode}</dd>
          <dt>
            <span id="productFamilyId">Product Family Id</span>
          </dt>
          <dd>{productEntity.productFamilyId}</dd>
          <dt>
            <span id="productPriceId">Product Price Id</span>
          </dt>
          <dd>{productEntity.productPriceId}</dd>
          <dt>
            <span id="productName">Product Name</span>
          </dt>
          <dd>{productEntity.productName}</dd>
          <dt>
            <span id="productFamilyCode">Product Family Code</span>
          </dt>
          <dd>{productEntity.productFamilyCode}</dd>
          <dt>
            <span id="productFamilyName">Product Family Name</span>
          </dt>
          <dd>{productEntity.productFamilyName}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
