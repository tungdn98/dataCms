import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './company.reducer';

export const CompanyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">Company</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="companyCode">Company Code</span>
          </dt>
          <dd>{companyEntity.companyCode}</dd>
          <dt>
            <span id="companyName">Company Name</span>
          </dt>
          <dd>{companyEntity.companyName}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{companyEntity.description}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{companyEntity.location}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{companyEntity.phoneNumber}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {companyEntity.createdDate ? <TextFormat value={companyEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{companyEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {companyEntity.lastModifiedDate ? (
              <TextFormat value={companyEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{companyEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;
