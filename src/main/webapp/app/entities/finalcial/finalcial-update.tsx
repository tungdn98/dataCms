import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFinalcial } from 'app/shared/model/finalcial.model';
import { getEntity, updateEntity, createEntity, reset } from './finalcial.reducer';

export const FinalcialUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const finalcialEntity = useAppSelector(state => state.finalcial.entity);
  const loading = useAppSelector(state => state.finalcial.loading);
  const updating = useAppSelector(state => state.finalcial.updating);
  const updateSuccess = useAppSelector(state => state.finalcial.updateSuccess);
  const handleClose = () => {
    props.history.push('/finalcial' + props.location.search);
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
      ...finalcialEntity,
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
          ...finalcialEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.finalcial.home.createOrEditLabel" data-cy="FinalcialCreateUpdateHeading">
            Create or edit a Finalcial
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="finalcial-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="finalcial-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Customer Name" id="finalcial-customerName" name="customerName" data-cy="customerName" type="text" />
              <ValidatedField
                label="Customer Short Name"
                id="finalcial-customerShortName"
                name="customerShortName"
                data-cy="customerShortName"
                type="text"
              />
              <ValidatedField label="Customer Type" id="finalcial-customerType" name="customerType" data-cy="customerType" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/finalcial" replace color="info">
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

export default FinalcialUpdate;
