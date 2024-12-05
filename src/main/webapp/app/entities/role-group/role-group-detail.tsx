import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './role-group.reducer';

export const RoleGroupDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roleGroupEntity = useAppSelector(state => state.roleGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roleGroupDetailsHeading">RoleGroup</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{roleGroupEntity.id}</dd>
          <dt>
            <span id="groupName">Group Name</span>
          </dt>
          <dd>{roleGroupEntity.groupName}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {roleGroupEntity.createdDate ? <TextFormat value={roleGroupEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{roleGroupEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {roleGroupEntity.lastModifiedDate ? (
              <TextFormat value={roleGroupEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{roleGroupEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/role-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/role-group/${roleGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoleGroupDetail;
