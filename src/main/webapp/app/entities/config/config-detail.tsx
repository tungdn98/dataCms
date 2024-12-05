import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './config.reducer';

export const ConfigDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const configEntity = useAppSelector(state => state.config.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="configDetailsHeading">Config</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{configEntity.id}</dd>
          <dt>
            <span id="configName">Config Name</span>
          </dt>
          <dd>{configEntity.configName}</dd>
          <dt>
            <span id="configValue">Config Value</span>
          </dt>
          <dd>{configEntity.configValue}</dd>
          <dt>
            <span id="configDesc">Config Desc</span>
          </dt>
          <dd>{configEntity.configDesc}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>{configEntity.createdDate ? <TextFormat value={configEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{configEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {configEntity.lastModifiedDate ? (
              <TextFormat value={configEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{configEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/config" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/config/${configEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConfigDetail;
