import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './emp-group.reducer';

export const EmpGroupDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const empGroupEntity = useAppSelector(state => state.empGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="empGroupDetailsHeading">EmpGroup</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{empGroupEntity.id}</dd>
          <dt>
            <span id="groupName">Group Name</span>
          </dt>
          <dd>{empGroupEntity.groupName}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {empGroupEntity.createdDate ? <TextFormat value={empGroupEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{empGroupEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {empGroupEntity.lastModifiedDate ? (
              <TextFormat value={empGroupEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{empGroupEntity.lastModifiedBy}</dd>
          <dt>Role</dt>
          <dd>
            {empGroupEntity.roles
              ? empGroupEntity.roles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {empGroupEntity.roles && i === empGroupEntity.roles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/emp-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emp-group/${empGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmpGroupDetail;
