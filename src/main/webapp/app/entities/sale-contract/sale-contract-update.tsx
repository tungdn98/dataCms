import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISaleContract } from 'app/shared/model/sale-contract.model';
import { getEntity, updateEntity, createEntity, reset } from './sale-contract.reducer';

export const SaleContractUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const saleContractEntity = useAppSelector(state => state.saleContract.entity);
  const loading = useAppSelector(state => state.saleContract.loading);
  const updating = useAppSelector(state => state.saleContract.updating);
  const updateSuccess = useAppSelector(state => state.saleContract.updateSuccess);
  const handleClose = () => {
    props.history.push('/sale-contract' + props.location.search);
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
      ...saleContractEntity,
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
          ...saleContractEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.saleContract.home.createOrEditLabel" data-cy="SaleContractCreateUpdateHeading">
            Create or edit a SaleContract
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
                <ValidatedField name="id" required readOnly id="sale-contract-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Contract Id" id="sale-contract-contractId" name="contractId" data-cy="contractId" type="text" />
              <ValidatedField label="Company Id" id="sale-contract-companyId" name="companyId" data-cy="companyId" type="text" />
              <ValidatedField label="Account Id" id="sale-contract-accountId" name="accountId" data-cy="accountId" type="text" />
              <ValidatedField
                label="Contact Signed Date"
                id="sale-contract-contactSignedDate"
                name="contactSignedDate"
                data-cy="contactSignedDate"
                type="date"
              />
              <ValidatedField
                label="Contact Signed Title"
                id="sale-contract-contactSignedTitle"
                name="contactSignedTitle"
                data-cy="contactSignedTitle"
                type="text"
              />
              <ValidatedField
                label="Contract End Date"
                id="sale-contract-contractEndDate"
                name="contractEndDate"
                data-cy="contractEndDate"
                type="date"
              />
              <ValidatedField
                label="Contract Number"
                id="sale-contract-contractNumber"
                name="contractNumber"
                data-cy="contractNumber"
                type="text"
              />
              <ValidatedField
                label="Contract Number Input"
                id="sale-contract-contractNumberInput"
                name="contractNumberInput"
                data-cy="contractNumberInput"
                type="text"
              />
              <ValidatedField
                label="Contract Stage Id"
                id="sale-contract-contractStageId"
                name="contractStageId"
                data-cy="contractStageId"
                type="text"
              />
              <ValidatedField
                label="Contract Start Date"
                id="sale-contract-contractStartDate"
                name="contractStartDate"
                data-cy="contractStartDate"
                type="date"
              />
              <ValidatedField
                label="Owner Employee Id"
                id="sale-contract-ownerEmployeeId"
                name="ownerEmployeeId"
                data-cy="ownerEmployeeId"
                type="text"
              />
              <ValidatedField
                label="Payment Method Id"
                id="sale-contract-paymentMethodId"
                name="paymentMethodId"
                data-cy="paymentMethodId"
                type="text"
              />
              <ValidatedField
                label="Contract Name"
                id="sale-contract-contractName"
                name="contractName"
                data-cy="contractName"
                type="text"
              />
              <ValidatedField
                label="Contract Type Id"
                id="sale-contract-contractTypeId"
                name="contractTypeId"
                data-cy="contractTypeId"
                type="text"
              />
              <ValidatedField label="Currency Id" id="sale-contract-currencyId" name="currencyId" data-cy="currencyId" type="text" />
              <ValidatedField label="Grand Total" id="sale-contract-grandTotal" name="grandTotal" data-cy="grandTotal" type="text" />
              <ValidatedField
                label="Payment Term Id"
                id="sale-contract-paymentTermId"
                name="paymentTermId"
                data-cy="paymentTermId"
                type="text"
              />
              <ValidatedField label="Quote Id" id="sale-contract-quoteId" name="quoteId" data-cy="quoteId" type="text" />
              <ValidatedField
                label="Currency Exchange Rate Id"
                id="sale-contract-currencyExchangeRateId"
                name="currencyExchangeRateId"
                data-cy="currencyExchangeRateId"
                type="text"
              />
              <ValidatedField
                label="Contract Stage Name"
                id="sale-contract-contractStageName"
                name="contractStageName"
                data-cy="contractStageName"
                type="text"
              />
              <ValidatedField
                label="Payment Status Id"
                id="sale-contract-paymentStatusId"
                name="paymentStatusId"
                data-cy="paymentStatusId"
                type="text"
              />
              <ValidatedField label="Period" id="sale-contract-period" name="period" data-cy="period" type="text" />
              <ValidatedField label="Payment" id="sale-contract-payment" name="payment" data-cy="payment" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sale-contract" replace color="info">
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

export default SaleContractUpdate;
