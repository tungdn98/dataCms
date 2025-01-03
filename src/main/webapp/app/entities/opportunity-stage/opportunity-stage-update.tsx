import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOpportunityStage } from 'app/shared/model/opportunity-stage.model';
import { getEntity, updateEntity, createEntity, reset } from './opportunity-stage.reducer';

export const OpportunityStageUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const opportunityStageEntity = useAppSelector(state => state.opportunityStage.entity);
  const loading = useAppSelector(state => state.opportunityStage.loading);
  const updating = useAppSelector(state => state.opportunityStage.updating);
  const updateSuccess = useAppSelector(state => state.opportunityStage.updateSuccess);
  const handleClose = () => {
    props.history.push('/opportunity-stage' + props.location.search);
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
      ...opportunityStageEntity,
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
          ...opportunityStageEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.opportunityStage.home.createOrEditLabel" data-cy="OpportunityStageCreateUpdateHeading">
            Create or edit a OpportunityStage
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
                <ValidatedField name="id" required readOnly id="opportunity-stage-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Opportunity Stage Id"
                id="opportunity-stage-opportunityStageId"
                name="opportunityStageId"
                data-cy="opportunityStageId"
                type="text"
              />
              <ValidatedField
                label="Opportunity Stage Name"
                id="opportunity-stage-opportunityStageName"
                name="opportunityStageName"
                data-cy="opportunityStageName"
                type="text"
              />
              <ValidatedField
                label="Opportunity Stage Code"
                id="opportunity-stage-opportunityStageCode"
                name="opportunityStageCode"
                data-cy="opportunityStageCode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opportunity-stage" replace color="info">
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

export default OpportunityStageUpdate;
