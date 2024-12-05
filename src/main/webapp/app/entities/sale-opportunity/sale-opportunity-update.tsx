import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISaleOpportunity } from 'app/shared/model/sale-opportunity.model';
import { getEntity, updateEntity, createEntity, reset } from './sale-opportunity.reducer';

export const SaleOpportunityUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const saleOpportunityEntity = useAppSelector(state => state.saleOpportunity.entity);
  const loading = useAppSelector(state => state.saleOpportunity.loading);
  const updating = useAppSelector(state => state.saleOpportunity.updating);
  const updateSuccess = useAppSelector(state => state.saleOpportunity.updateSuccess);
  const handleClose = () => {
    props.history.push('/sale-opportunity' + props.location.search);
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
    const entity = {
      ...saleOpportunityEntity,
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
      ? {}
      : {
          ...saleOpportunityEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.saleOpportunity.home.createOrEditLabel" data-cy="SaleOpportunityCreateUpdateHeading">
            Create or edit a SaleOpportunity
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="sale-opportunity-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Opportunity Id"
                id="sale-opportunity-opportunityId"
                name="opportunityId"
                data-cy="opportunityId"
                type="text"
              />
              <ValidatedField
                label="Opportunity Code"
                id="sale-opportunity-opportunityCode"
                name="opportunityCode"
                data-cy="opportunityCode"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Opportunity Name"
                id="sale-opportunity-opportunityName"
                name="opportunityName"
                data-cy="opportunityName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Opportunity Type Name"
                id="sale-opportunity-opportunityTypeName"
                name="opportunityTypeName"
                data-cy="opportunityTypeName"
                type="text"
              />
              <ValidatedField label="Start Date" id="sale-opportunity-startDate" name="startDate" data-cy="startDate" type="date" />
              <ValidatedField label="Close Date" id="sale-opportunity-closeDate" name="closeDate" data-cy="closeDate" type="date" />
              <ValidatedField label="Stage Id" id="sale-opportunity-stageId" name="stageId" data-cy="stageId" type="text" />
              <ValidatedField
                label="Stage Reason Id"
                id="sale-opportunity-stageReasonId"
                name="stageReasonId"
                data-cy="stageReasonId"
                type="text"
              />
              <ValidatedField label="Employee Id" id="sale-opportunity-employeeId" name="employeeId" data-cy="employeeId" type="text" />
              <ValidatedField label="Lead Id" id="sale-opportunity-leadId" name="leadId" data-cy="leadId" type="text" />
              <ValidatedField
                label="Currency Code"
                id="sale-opportunity-currencyCode"
                name="currencyCode"
                data-cy="currencyCode"
                type="text"
              />
              <ValidatedField label="Account Id" id="sale-opportunity-accountId" name="accountId" data-cy="accountId" type="text" />
              <ValidatedField label="Product Id" id="sale-opportunity-productId" name="productId" data-cy="productId" type="text" />
              <ValidatedField
                label="Sales Price Prd"
                id="sale-opportunity-salesPricePrd"
                name="salesPricePrd"
                data-cy="salesPricePrd"
                type="text"
              />
              <ValidatedField label="Value" id="sale-opportunity-value" name="value" data-cy="value" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sale-opportunity" replace color="info">
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

export default SaleOpportunityUpdate;
