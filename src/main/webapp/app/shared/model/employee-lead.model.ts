export interface IEmployeeLead {
  id?: number;
  leadId?: string | null;
  employeeId?: string | null;
  leadCode?: string;
  leadName?: string | null;
  leadPotentialLevelId?: string | null;
  leadSourceId?: string | null;
  leadPotentialLevelName?: string | null;
  leadSourceName?: string | null;
}

export class EmployeeLeadImport {
  STT: number | null = null;
  leadId?: string | null = null;
  employeeId?: string | null = null;
  leadCode?: string | null = null;
  leadName?: string | null = null;
  leadPotentialLevelId?: string | null = null;
  leadSourceId?: string | null = null;
  leadPotentialLevelName?: string | null = null;
  leadSourceName?: string | null = null;
}

export const defaultValue: Readonly<IEmployeeLead> = {};
