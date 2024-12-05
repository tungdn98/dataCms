import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-lead.reducer';

export const EmployeeLeadDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const employeeLeadEntity = useAppSelector(state => state.employeeLead.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeLeadDetailsHeading">EmployeeLead</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeLeadEntity.id}</dd>
          <dt>
            <span id="leadId">Lead Id</span>
          </dt>
          <dd>{employeeLeadEntity.leadId}</dd>
          <dt>
            <span id="employeeId">Employee Id</span>
          </dt>
          <dd>{employeeLeadEntity.employeeId}</dd>
          <dt>
            <span id="leadCode">Lead Code</span>
          </dt>
          <dd>{employeeLeadEntity.leadCode}</dd>
          <dt>
            <span id="leadName">Lead Name</span>
          </dt>
          <dd>{employeeLeadEntity.leadName}</dd>
          <dt>
            <span id="leadPotentialLevelId">Lead Potential Level Id</span>
          </dt>
          <dd>{employeeLeadEntity.leadPotentialLevelId}</dd>
          <dt>
            <span id="leadSourceId">Lead Source Id</span>
          </dt>
          <dd>{employeeLeadEntity.leadSourceId}</dd>
          <dt>
            <span id="leadPotentialLevelName">Lead Potential Level Name</span>
          </dt>
          <dd>{employeeLeadEntity.leadPotentialLevelName}</dd>
          <dt>
            <span id="leadSourceName">Lead Source Name</span>
          </dt>
          <dd>{employeeLeadEntity.leadSourceName}</dd>
        </dl>
        <Button tag={Link} to="/employee-lead" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-lead/${employeeLeadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeLeadDetail;
