import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OpportunityStage from './opportunity-stage';
import OpportunityStageDetail from './opportunity-stage-detail';
import OpportunityStageUpdate from './opportunity-stage-update';
import OpportunityStageDeleteDialog from './opportunity-stage-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OpportunityStageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OpportunityStageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OpportunityStageDetail} />
      <ErrorBoundaryRoute path={match.url} component={OpportunityStage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OpportunityStageDeleteDialog} />
  </>
);

export default Routes;
