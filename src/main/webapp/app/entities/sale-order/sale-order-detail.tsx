import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sale-order.reducer';

export const SaleOrderDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const saleOrderEntity = useAppSelector(state => state.saleOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="saleOrderDetailsHeading">SaleOrder</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{saleOrderEntity.id}</dd>
          <dt>
            <span id="orderId">Order Id</span>
          </dt>
          <dd>{saleOrderEntity.orderId}</dd>
          <dt>
            <span id="contractId">Contract Id</span>
          </dt>
          <dd>{saleOrderEntity.contractId}</dd>
          <dt>
            <span id="ownerEmployeeId">Owner Employee Id</span>
          </dt>
          <dd>{saleOrderEntity.ownerEmployeeId}</dd>
          <dt>
            <span id="productId">Product Id</span>
          </dt>
          <dd>{saleOrderEntity.productId}</dd>
          <dt>
            <span id="totalValue">Total Value</span>
          </dt>
          <dd>{saleOrderEntity.totalValue}</dd>
          <dt>
            <span id="orderStageId">Order Stage Id</span>
          </dt>
          <dd>{saleOrderEntity.orderStageId}</dd>
          <dt>
            <span id="orderStageName">Order Stage Name</span>
          </dt>
          <dd>{saleOrderEntity.orderStageName}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {saleOrderEntity.createdDate ? <TextFormat value={saleOrderEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{saleOrderEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {saleOrderEntity.lastModifiedDate ? (
              <TextFormat value={saleOrderEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{saleOrderEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/sale-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sale-order/${saleOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SaleOrderDetail;
