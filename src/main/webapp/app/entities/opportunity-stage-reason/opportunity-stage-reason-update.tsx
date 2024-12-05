import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOpportunityStageReason } from 'app/shared/model/opportunity-stage-reason.model';
import { getEntity, updateEntity, createEntity, reset } from './opportunity-stage-reason.reducer';

export const OpportunityStageReasonUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const opportunityStageReasonEntity = useAppSelector(state => state.opportunityStageReason.entity);
  const loading = useAppSelector(state => state.opportunityStageReason.loading);
  const updating = useAppSelector(state => state.opportunityStageReason.updating);
  const updateSuccess = useAppSelector(state => state.opportunityStageReason.updateSuccess);
  const handleClose = () => {
    props.history.push('/opportunity-stage-reason' + props.location.search);
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
      ...opportunityStageReasonEntity,
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
          ...opportunityStageReasonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.opportunityStageReason.home.createOrEditLabel" data-cy="OpportunityStageReasonCreateUpdateHeading">
            Create or edit a OpportunityStageReason
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
                <ValidatedField name="id" required readOnly id="opportunity-stage-reason-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Opportunity Stage Reason Id"
                id="opportunity-stage-reason-opportunityStageReasonId"
                name="opportunityStageReasonId"
                data-cy="opportunityStageReasonId"
                type="text"
              />
              <ValidatedField
                label="Opportunity Stage Id"
                id="opportunity-stage-reason-opportunityStageId"
                name="opportunityStageId"
                data-cy="opportunityStageId"
                type="text"
              />
              <ValidatedField
                label="Opportunity Stage Reason Name"
                id="opportunity-stage-reason-opportunityStageReasonName"
                name="opportunityStageReasonName"
                data-cy="opportunityStageReasonName"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opportunity-stage-reason" replace color="info">
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

export default OpportunityStageReasonUpdate;
