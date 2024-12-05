import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sale-contract.reducer';

export const SaleContractDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const saleContractEntity = useAppSelector(state => state.saleContract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="saleContractDetailsHeading">SaleContract</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{saleContractEntity.id}</dd>
          <dt>
            <span id="contractId">Contract Id</span>
          </dt>
          <dd>{saleContractEntity.contractId}</dd>
          <dt>
            <span id="companyId">Company Id</span>
          </dt>
          <dd>{saleContractEntity.companyId}</dd>
          <dt>
            <span id="accountId">Account Id</span>
          </dt>
          <dd>{saleContractEntity.accountId}</dd>
          <dt>
            <span id="contactSignedDate">Contact Signed Date</span>
          </dt>
          <dd>
            {saleContractEntity.contactSignedDate ? (
              <TextFormat value={saleContractEntity.contactSignedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contactSignedTitle">Contact Signed Title</span>
          </dt>
          <dd>{saleContractEntity.contactSignedTitle}</dd>
          <dt>
            <span id="contractEndDate">Contract End Date</span>
          </dt>
          <dd>
            {saleContractEntity.contractEndDate ? (
              <TextFormat value={saleContractEntity.contractEndDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contractNumber">Contract Number</span>
          </dt>
          <dd>{saleContractEntity.contractNumber}</dd>
          <dt>
            <span id="contractNumberInput">Contract Number Input</span>
          </dt>
          <dd>{saleContractEntity.contractNumberInput}</dd>
          <dt>
            <span id="contractStageId">Contract Stage Id</span>
          </dt>
          <dd>{saleContractEntity.contractStageId}</dd>
          <dt>
            <span id="contractStartDate">Contract Start Date</span>
          </dt>
          <dd>
            {saleContractEntity.contractStartDate ? (
              <TextFormat value={saleContractEntity.contractStartDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ownerEmployeeId">Owner Employee Id</span>
          </dt>
          <dd>{saleContractEntity.ownerEmployeeId}</dd>
          <dt>
            <span id="paymentMethodId">Payment Method Id</span>
          </dt>
          <dd>{saleContractEntity.paymentMethodId}</dd>
          <dt>
            <span id="contractName">Contract Name</span>
          </dt>
          <dd>{saleContractEntity.contractName}</dd>
          <dt>
            <span id="contractTypeId">Contract Type Id</span>
          </dt>
          <dd>{saleContractEntity.contractTypeId}</dd>
          <dt>
            <span id="currencyId">Currency Id</span>
          </dt>
          <dd>{saleContractEntity.currencyId}</dd>
          <dt>
            <span id="grandTotal">Grand Total</span>
          </dt>
          <dd>{saleContractEntity.grandTotal}</dd>
          <dt>
            <span id="paymentTermId">Payment Term Id</span>
          </dt>
          <dd>{saleContractEntity.paymentTermId}</dd>
          <dt>
            <span id="quoteId">Quote Id</span>
          </dt>
          <dd>{saleContractEntity.quoteId}</dd>
          <dt>
            <span id="currencyExchangeRateId">Currency Exchange Rate Id</span>
          </dt>
          <dd>{saleContractEntity.currencyExchangeRateId}</dd>
          <dt>
            <span id="contractStageName">Contract Stage Name</span>
          </dt>
          <dd>{saleContractEntity.contractStageName}</dd>
          <dt>
            <span id="paymentStatusId">Payment Status Id</span>
          </dt>
          <dd>{saleContractEntity.paymentStatusId}</dd>
          <dt>
            <span id="period">Period</span>
          </dt>
          <dd>{saleContractEntity.period}</dd>
          <dt>
            <span id="payment">Payment</span>
          </dt>
          <dd>{saleContractEntity.payment}</dd>
        </dl>
        <Button tag={Link} to="/sale-contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sale-contract/${saleContractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SaleContractDetail;
