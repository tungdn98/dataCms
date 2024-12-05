import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './config-log.reducer';

export const ConfigLogDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const configLogEntity = useAppSelector(state => state.configLog.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="configLogDetailsHeading">ConfigLog</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{configLogEntity.id}</dd>
          <dt>
            <span id="configName">Config Name</span>
          </dt>
          <dd>{configLogEntity.configName}</dd>
          <dt>
            <span id="valueBefore">Value Before</span>
          </dt>
          <dd>{configLogEntity.valueBefore}</dd>
          <dt>
            <span id="valueAfter">Value After</span>
          </dt>
          <dd>{configLogEntity.valueAfter}</dd>
          <dt>
            <span id="modifiedDate">Modified Date</span>
          </dt>
          <dd>
            {configLogEntity.modifiedDate ? <TextFormat value={configLogEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiedUsername">Modified Username</span>
          </dt>
          <dd>{configLogEntity.modifiedUsername}</dd>
          <dt>
            <span id="modifiedFullname">Modified Fullname</span>
          </dt>
          <dd>{configLogEntity.modifiedFullname}</dd>
        </dl>
        <Button tag={Link} to="/config-log" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/config-log/${configLogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConfigLogDetail;
