import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISaleOrder } from 'app/shared/model/sale-order.model';
import { getEntity, updateEntity, createEntity, reset } from './sale-order.reducer';

export const SaleOrderUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const saleOrderEntity = useAppSelector(state => state.saleOrder.entity);
  const loading = useAppSelector(state => state.saleOrder.loading);
  const updating = useAppSelector(state => state.saleOrder.updating);
  const updateSuccess = useAppSelector(state => state.saleOrder.updateSuccess);
  const handleClose = () => {
    props.history.push('/sale-order' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    const entity = {
      ...saleOrderEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdDate: displayDefaultDateTime(),
          lastModifiedDate: displayDefaultDateTime(),
        }
      : {
          ...saleOrderEntity,
          createdDate: convertDateTimeFromServer(saleOrderEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(saleOrderEntity.lastModifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.saleOrder.home.createOrEditLabel" data-cy="SaleOrderCreateUpdateHeading">
            Create or edit a SaleOrder
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sale-order-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Order Id" id="sale-order-orderId" name="orderId" data-cy="orderId" type="text" />
              <ValidatedField label="Contract Id" id="sale-order-contractId" name="contractId" data-cy="contractId" type="text" />
              <ValidatedField
                label="Owner Employee Id"
                id="sale-order-ownerEmployeeId"
                name="ownerEmployeeId"
                data-cy="ownerEmployeeId"
                type="text"
              />
              <ValidatedField label="Product Id" id="sale-order-productId" name="productId" data-cy="productId" type="text" />
              <ValidatedField label="Total Value" id="sale-order-totalValue" name="totalValue" data-cy="totalValue" type="text" />
              <ValidatedField label="Order Stage Id" id="sale-order-orderStageId" name="orderStageId" data-cy="orderStageId" type="text" />
              <ValidatedField
                label="Order Stage Name"
                id="sale-order-orderStageName"
                name="orderStageName"
                data-cy="orderStageName"
                type="text"
              />
              <ValidatedField
                label="Created Date"
                id="sale-order-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="sale-order-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Last Modified Date"
                id="sale-order-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="sale-order-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sale-order" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SaleOrderUpdate;
