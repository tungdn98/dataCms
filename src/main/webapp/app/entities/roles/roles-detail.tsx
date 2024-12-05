import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './roles.reducer';

export const RolesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const rolesEntity = useAppSelector(state => state.roles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rolesDetailsHeading">Roles</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rolesEntity.id}</dd>
          <dt>
            <span id="resourceUrl">Resource Url</span>
          </dt>
          <dd>{rolesEntity.resourceUrl}</dd>
          <dt>
            <span id="resourceDesc">Resource Desc</span>
          </dt>
          <dd>{rolesEntity.resourceDesc}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>{rolesEntity.createdDate ? <TextFormat value={rolesEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{rolesEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {rolesEntity.lastModifiedDate ? <TextFormat value={rolesEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{rolesEntity.lastModifiedBy}</dd>
          <dt>Role Group</dt>
          <dd>{rolesEntity.roleGroup ? rolesEntity.roleGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/roles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/roles/${rolesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RolesDetail;
