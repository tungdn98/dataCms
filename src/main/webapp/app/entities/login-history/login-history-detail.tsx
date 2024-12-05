import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './login-history.reducer';

export const LoginHistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const loginHistoryEntity = useAppSelector(state => state.loginHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loginHistoryDetailsHeading">LoginHistory</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{loginHistoryEntity.id}</dd>
          <dt>
            <span id="empCode">Emp Code</span>
          </dt>
          <dd>{loginHistoryEntity.empCode}</dd>
          <dt>
            <span id="empUsername">Emp Username</span>
          </dt>
          <dd>{loginHistoryEntity.empUsername}</dd>
          <dt>
            <span id="empFullName">Emp Full Name</span>
          </dt>
          <dd>{loginHistoryEntity.empFullName}</dd>
          <dt>
            <span id="loginIp">Login Ip</span>
          </dt>
          <dd>{loginHistoryEntity.loginIp}</dd>
          <dt>
            <span id="loginTime">Login Time</span>
          </dt>
          <dd>
            {loginHistoryEntity.loginTime ? <TextFormat value={loginHistoryEntity.loginTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/login-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/login-history/${loginHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoginHistoryDetail;
