import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">Employee</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="employeeCode">Employee Code</span>
          </dt>
          <dd>{employeeEntity.employeeCode}</dd>
          <dt>
            <span id="employeeName">Employee Name</span>
          </dt>
          <dd>{employeeEntity.employeeName}</dd>
          <dt>
            <span id="username">Username</span>
          </dt>
          <dd>{employeeEntity.username}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{employeeEntity.password}</dd>
          <dt>
            <span id="active">Active</span>
          </dt>
          <dd>{employeeEntity.active}</dd>
          <dt>
            <span id="companyCode">Company Code</span>
          </dt>
          <dd>{employeeEntity.companyCode}</dd>
          <dt>
            <span id="companyName">Company Name</span>
          </dt>
          <dd>{employeeEntity.companyName}</dd>
          <dt>
            <span id="organizationId">Organization Id</span>
          </dt>
          <dd>{employeeEntity.organizationId}</dd>
          <dt>
            <span id="employeeLastName">Employee Last Name</span>
          </dt>
          <dd>{employeeEntity.employeeLastName}</dd>
          <dt>
            <span id="employeeMiddleName">Employee Middle Name</span>
          </dt>
          <dd>{employeeEntity.employeeMiddleName}</dd>
          <dt>
            <span id="employeeTitleId">Employee Title Id</span>
          </dt>
          <dd>{employeeEntity.employeeTitleId}</dd>
          <dt>
            <span id="employeeTitleName">Employee Title Name</span>
          </dt>
          <dd>{employeeEntity.employeeTitleName}</dd>
          <dt>
            <span id="employeeFullName">Employee Full Name</span>
          </dt>
          <dd>{employeeEntity.employeeFullName}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {employeeEntity.createdDate ? <TextFormat value={employeeEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{employeeEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {employeeEntity.lastModifiedDate ? (
              <TextFormat value={employeeEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{employeeEntity.lastModifiedBy}</dd>
          <dt>Emp Group</dt>
          <dd>{employeeEntity.empGroup ? employeeEntity.empGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
