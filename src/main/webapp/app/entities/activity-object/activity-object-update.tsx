import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivityObject } from 'app/shared/model/activity-object.model';
import { getEntity, updateEntity, createEntity, reset } from './activity-object.reducer';

export const ActivityObjectUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const activityObjectEntity = useAppSelector(state => state.activityObject.entity);
  const loading = useAppSelector(state => state.activityObject.loading);
  const updating = useAppSelector(state => state.activityObject.updating);
  const updateSuccess = useAppSelector(state => state.activityObject.updateSuccess);
  const handleClose = () => {
    props.history.push('/activity-object' + props.location.search);
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
      ...activityObjectEntity,
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
          ...activityObjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.activityObject.home.createOrEditLabel" data-cy="ActivityObjectCreateUpdateHeading">
            Create or edit a ActivityObject
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
                <ValidatedField name="id" required readOnly id="activity-object-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Unit Code" id="activity-object-unitCode" name="unitCode" data-cy="unitCode" type="text" />
              <ValidatedField label="Unit Name" id="activity-object-unitName" name="unitName" data-cy="unitName" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity-object" replace color="info">
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

export default ActivityObjectUpdate;
