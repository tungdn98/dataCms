import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SaleOpportunity from './sale-opportunity';
import SaleOpportunityDetail from './sale-opportunity-detail';
import SaleOpportunityUpdate from './sale-opportunity-update';
import SaleOpportunityDeleteDialog from './sale-opportunity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SaleOpportunityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SaleOpportunityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SaleOpportunityDetail} />
      <ErrorBoundaryRoute path={match.url} component={SaleOpportunity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SaleOpportunityDeleteDialog} />
  </>
);

export default Routes;
