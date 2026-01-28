# AZ-305 Exam Preparation: Design Identity, Governance, and Monitoring Solutions (25-30%)

> **Source:** Official Microsoft Learn Documentation  
> **Last Updated:** January 2026  
> **Exam Weight:** 25-30% of total exam

---

## 📋 Table of Contents
1. [Design Solutions for Logging and Monitoring](#1-design-solutions-for-logging-and-monitoring)
2. [Design Authentication and Authorization Solutions](#2-design-authentication-and-authorization-solutions)
3. [Design Governance](#3-design-governance)

---

## 1. Design Solutions for Logging and Monitoring

### 1.1 Azure Monitor Fundamentals

#### **Key Concepts**
- **Azure Monitor** is the unified platform for monitoring Azure resources, applications, and infrastructure
- Collects **metrics** (numerical time-series data) and **logs** (event-based data)
- Two primary data stores:
  - **Metrics Store**: Near real-time, optimized for time-series data
  - **Log Analytics Workspace**: Long-term storage, powerful query engine (KQL)

#### **Diagnostic Settings**
- **Purpose**: Route platform logs and metrics from Azure resources to destinations
- **Configuration Requirements**:
  - Must be created for EACH resource (or subscription/management group)
  - Can have up to **5 diagnostic settings per resource**
  - Each setting can define **1 destination per type**
  
- **Available Destinations**:
  1. **Log Analytics Workspace** - Query with KQL, integrate with alerts
  2. **Azure Storage Account** - Long-term archival, lower cost
  3. **Event Hubs** - Stream to external SIEM/analytics tools
  4. **Partner Integrations** - Third-party monitoring solutions

- **Log Categories**:
  - **Resource Logs** (formerly diagnostic logs): Service-specific
  - **Activity Log**: Subscription-level control plane operations
  - **Metrics**: Platform metrics (already collected, diagnostic settings send to logs)

- **Configuration Methods**:
  - Azure Portal (per resource)
  - Azure CLI: `az monitor diagnostic-settings create`
  - PowerShell: `New-AzDiagnosticSetting`
  - ARM/Bicep templates (at scale)
  - Azure Policy (enforce across resources)

#### **Log Analytics Workspace**
- **Centralized repository** for logs from multiple sources
- **Retention**:
  - Default: 30 days (configurable up to 730 days)
  - Archive tier available for long-term storage
  
- **Query Language**: Kusto Query Language (KQL)
- **Access Control**:
  - Workspace-level: All tables accessible
  - Resource-level: Only logs from specific resources
  - Table-level RBAC: Granular access per table

- **Key Tables**:
  - `AzureActivity`: Activity log entries
  - `AzureDiagnostics`: Resource logs (legacy schema)
  - Resource-specific tables (e.g., `AppServiceHTTPLogs`)

#### **Application Insights**
- **Purpose**: Application performance monitoring (APM)
- **Architecture**:
  - Workspace-based (recommended): Data stored in Log Analytics
  - Classic (legacy): Separate storage
  
- **Telemetry Types**:
  - Requests, dependencies, exceptions, page views
  - Custom events and metrics
  - Distributed tracing
  
- **Diagnostic Settings**: Can export to additional destinations beyond workspace

#### **Monitoring Recommendations**
- **Enable diagnostic settings** for all critical resources
- **Use Log Analytics** for centralized querying and correlation
- **Storage accounts** for compliance/audit archival
- **Event Hubs** for real-time streaming to external systems
- **Set up alerts** based on log queries and metrics
- **Cost management**: Select only required log categories

---

## 2. Design Authentication and Authorization Solutions

### 2.1 Microsoft Entra ID (formerly Azure AD)

#### **Core Identity Concepts**
- **Microsoft Entra ID** is Azure's cloud-based identity and access management service
- **Tenant**: Organization's dedicated instance of Entra ID
- **Directory**: Contains users, groups, applications, and devices
- **Single Tenant** per Azure subscription (but subscriptions can trust the same tenant)

#### **Identity Types**
1. **Cloud Identities**: Created directly in Entra ID
2. **Synchronized Identities**: On-premises AD synced via Entra Connect
3. **Guest Users (B2B)**: External users invited to tenant
4. **B2C Identities**: Customer-facing identity management

#### **Authentication Methods**
- **Password + MFA** (Multi-Factor Authentication)
- **Passwordless**:
  - Windows Hello for Business
  - FIDO2 security keys
  - Microsoft Authenticator app
- **Certificate-based authentication**
- **Modern authentication protocols**: OAuth 2.0, OpenID Connect, SAML

---

### 2.2 Conditional Access

#### **Purpose**
Policy engine that enforces access controls based on signals (who, what, where, how)

#### **Key Components**
1. **Signals/Conditions**:
   - User or workload identity
   - Group membership
   - IP location (named locations)
   - Device platform (iOS, Android, Windows, macOS)
   - Device state (compliant, hybrid joined)
   - Application (cloud apps)
   - Risk level (sign-in risk, user risk - requires Identity Protection)
   - Real-time session risk

2. **Access Controls**:
   - **Grant**: 
     - Block access
     - Grant access (with requirements):
       - Require MFA
       - Require device to be compliant
       - Require hybrid Azure AD joined device
       - Require approved client app
       - Require app protection policy
   - **Session**: 
     - Use app enforced restrictions
     - Conditional Access App Control (MCAS)
     - Sign-in frequency
     - Persistent browser session

3. **Policy Modes**:
   - **Report-only**: Evaluate but don't enforce (testing)
   - **On**: Enforce policy
   - **Off**: Disabled

#### **Best Practices**
- **Exclude break-glass accounts** (emergency access)
- Start with **report-only mode** to test impact
- Use **named locations** for trusted IPs
- Combine multiple conditions using AND/OR logic
- Apply to **all cloud apps** for comprehensive protection
- **Require MFA** for all users, especially admins
- Require compliant/managed devices for sensitive apps

#### **Common Scenarios**
- Block legacy authentication
- Require MFA for administrators
- Require MFA for Azure management
- Block access from untrusted locations
- Require managed devices for Office 365

---

### 2.3 Azure Role-Based Access Control (Azure RBAC)

#### **Core Concepts**
- **Authorization mechanism** for Azure resources (management plane + data plane)
- **Role Assignment** = Security Principal + Role Definition + Scope

#### **Components**

**1. Security Principal** (WHO):
   - User
   - Group (best practice for management)
   - Service Principal (application identity)
   - Managed Identity (system-assigned or user-assigned)

**2. Role Definition** (WHAT):
   - Collection of permissions (actions)
   - **Actions**: Management operations (`Microsoft.Compute/virtualMachines/start/action`)
   - **DataActions**: Data plane operations (`Microsoft.Storage/storageAccounts/blobServices/containers/blobs/read`)
   - **NotActions/NotDataActions**: Exclusions

**3. Scope** (WHERE):
   - Hierarchy (permissions **inherit downward**):
     ```
     Management Group (up to 6 levels)
       └─ Subscription
           └─ Resource Group
               └─ Resource
     ```
   - **Inheritance**: Child scopes inherit permissions from parent
   - **Best Practice**: Grant at highest level needed, use least privilege

#### **Built-in Roles** (Exam-Critical)
- **Owner**: Full access + can delegate access (assign roles)
- **Contributor**: Full access except cannot assign roles
- **Reader**: Read-only access
- **User Access Administrator**: Manage user access (no resource management)

**Specialized Roles**:
- **Key Vault Administrator**, **Key Vault Secrets Officer**, **Key Vault Secrets User**
- **Storage Blob Data Owner/Contributor/Reader**
- **Virtual Machine Contributor**
- **Network Contributor**

#### **Custom Roles**
- Create when built-in roles don't meet requirements
- Define:
  - `Actions` and `NotActions`
  - `DataActions` and `NotDataActions`
  - `AssignableScopes`: Where role can be assigned
- **Limit**: 5,000 custom roles per tenant

#### **Best Practices**
- **Use groups** for role assignments (not individual users)
- **Least privilege principle**: Grant minimum permissions
- **Limit Owner/Contributor** assignments at subscription level
- **Use custom roles** for specific scenarios
- **Audit regularly**: Review role assignments with access reviews
- **Separate duties**: Don't combine Owner + Contributor roles

#### **Azure RBAC vs. Entra ID Roles**
- **Azure RBAC**: Manages access to **Azure resources** (VMs, storage, networks)
- **Entra ID Roles**: Manages **Entra ID objects** (users, groups, applications)
- **Separate permission systems** (with some overlap like Owner role)

---

### 2.4 Managed Identities

#### **Purpose**
Eliminate need for credentials in code when accessing Azure services

#### **Types**

**1. System-Assigned Managed Identity**:
   - Lifecycle tied to Azure resource (VM, App Service, Function)
   - Created/deleted with resource
   - 1:1 relationship with resource
   - **Use when**: Single resource needs identity

**2. User-Assigned Managed Identity**:
   - Standalone Azure resource
   - Can be shared across multiple resources
   - Independent lifecycle
   - **Use when**: Multiple resources need same identity OR identity outlives resource

#### **How It Works**
1. Enable managed identity on resource
2. Azure creates service principal in Entra ID
3. Azure manages credentials (automatic rotation)
4. Application uses Azure SDK to get token from Azure Instance Metadata Service (IMDS)
5. Token used to authenticate to Azure services (Key Vault, Storage, SQL, etc.)

#### **Supported Services**
- Azure VMs, VM Scale Sets
- Azure App Service, Functions
- Azure Container Instances
- Azure Kubernetes Service (workload identity)
- Azure Logic Apps, Data Factory
- **NOT all services** - check documentation

#### **Best Practices**
- **Prefer managed identities** over service principals
- Use **system-assigned** for single-resource scenarios
- Use **user-assigned** for shared identity scenarios
- Assign **RBAC roles** to managed identity at appropriate scope
- Use with **Azure Key Vault** to retrieve secrets/certificates

---

### 2.5 Service Principals

#### **Definition**
Application identity in Entra ID - security principal representing an application

#### **When to Use**
- Application runs **outside Azure** (on-premises, other clouds)
- Service doesn't support managed identities
- Automation/DevOps tools (Terraform, GitHub Actions)
- Third-party integrations

#### **Authentication Methods**
1. **Client Secret** (password):
   - Time-limited (max 2 years)
   - Must be rotated manually
   - Store in Azure Key Vault
   - **Least secure option**

2. **Certificate**:
   - More secure than secrets
   - Can be self-signed or CA-issued
   - Rotation required
   - Store private key securely

#### **Best Practices**
- **Use managed identities** when possible
- **Limit credential lifetime** (shorter is better)
- **Store credentials in Key Vault**
- **Rotate regularly** (automated process)
- **Principle of least privilege**: Assign minimal permissions
- **Monitor usage**: Track with audit logs

#### **Creating Service Principals**
- Azure Portal: App Registrations
- Azure CLI: `az ad sp create-for-rbac`
- PowerShell: `New-AzADServicePrincipal`
- Terraform/automation tools

---

### 2.6 Azure Key Vault

#### **Purpose**
Centralized secrets management, key management, and certificate management

#### **Vault Types**
1. **Standard**: Software-protected keys
2. **Premium**: HSM-protected keys (FIPS 140-3 Level 3)

#### **Object Types**

**1. Secrets**:
   - Passwords, connection strings, API keys
   - Max size: 25 KB
   - Versioned (track changes)
   - **Store as**: Connection strings, multi-part credentials as JSON

**2. Keys**:
   - Cryptographic keys (RSA, EC)
   - Software or HSM-protected
   - Operations: Encrypt, decrypt, sign, verify, wrap, unwrap
   - Bring Your Own Key (BYOK)

**3. Certificates**:
   - SSL/TLS certificates
   - Integrated with CAs (DigiCert, GlobalSign)
   - Auto-renewal
   - Stored as: Secret (PFX) + Key (public/private)

#### **Access Control**

**1. Access Policies (Classic)**:
   - Grant permissions per object type (keys, secrets, certificates)
   - Assigned to: User, group, service principal, managed identity
   - Permissions: Get, List, Set, Delete, Backup, Restore, Purge, etc.
   
**2. RBAC (Recommended)**:
   - **Key Vault Administrator**: Full access to vault and objects
   - **Key Vault Secrets Officer**: Manage secrets lifecycle
   - **Key Vault Secrets User**: Read secret values
   - **Key Vault Certificates Officer**: Manage certificates
   - **Key Vault Crypto Officer**: Manage keys
   - Assigned at: Vault scope or individual object scope

#### **Security Features**
- **Soft Delete**: Deleted objects retained 7-90 days (recovery possible)
- **Purge Protection**: Prevents permanent deletion during retention period
- **Network Isolation**:
  - Private endpoints (VNet integration)
  - Firewall rules (allow specific IPs/VNets)
  - Service endpoints
- **Logging**: Diagnostic settings to Log Analytics/Storage/Event Hub
- **Managed HSM**: Dedicated HSM for sensitive keys (separate service)

#### **Best Practices**
- **Enable soft delete and purge protection** (production)
- **Use RBAC** over access policies (modern approach)
- **Separate vaults** per environment (dev, test, prod)
- **Use managed identities** to access Key Vault
- **Enable logging** for audit trail
- **Set expiration dates** on secrets/certificates
- **Auto-rotate** where possible (certificates, secrets)
- **Network isolation** for production vaults
- **Monitor with Azure Monitor** (alerts on access/modifications)

#### **Integration**
- App Service: Reference as app settings `@Microsoft.KeyVault(SecretUri=...)`
- VMs: Key Vault VM extension
- Azure Pipelines: Azure Key Vault task
- Kubernetes: CSI driver, secrets store

---

### 2.7 Privileged Identity Management (PIM)

#### **Purpose**
Just-in-time (JIT) privileged access management for Azure and Entra ID roles

#### **Requires**: Microsoft Entra ID P2 or Microsoft Entra ID Governance license

#### **Key Concepts**

**1. Eligible vs. Active Assignments**:
   - **Eligible**: User can **activate** role when needed (JIT)
   - **Active**: Permanent assignment (always has permissions)
   - **Time-bound**: Both can have start/end dates

**2. Activation**:
   - User requests activation via portal/API
   - Can require:
     - Justification (business reason)
     - MFA challenge
     - Approval (designated approver)
     - Ticket number
   - Duration: Configurable (e.g., 8 hours max)

**3. Role Settings**:
   - Activation requirements (MFA, approval, justification)
   - Maximum activation duration
   - Require notification on activation
   - Require approval to activate
   - Assignment duration (eligible/active)

#### **Supported Scopes**
- **Entra ID Roles**: Global Admin, User Admin, Privileged Role Admin, etc.
- **Azure Resources**: Owner, Contributor, User Access Admin at subscription/resource group/resource
- **Groups**: PIM for Groups (role-assignable security groups)

#### **Access Reviews in PIM**
- **Purpose**: Periodic review of privileged role assignments
- **Reviewers**: 
  - Members (self-review)
  - Manager
  - Specific users
- **Frequency**: One-time, weekly, monthly, quarterly, annually
- **Auto-apply**: Automatically remove access if denied
- **Recommendations**: Azure provides AI-based recommendations

#### **Benefits**
- **Reduce attack surface**: Minimize standing privileged access
- **Audit trail**: All activations logged
- **Compliance**: Meet regulatory requirements
- **Accountability**: Justification + approval workflow

#### **Best Practices**
- **Make privileged roles eligible** (not active)
- **Require MFA** for activation
- **Require justification** for all activations
- **Set maximum activation duration** (4-8 hours)
- **Enable access reviews** (quarterly/annually)
- **Use PIM for Groups** to manage group memberships
- **Monitor alerts**: Track suspicious activations

---

### 2.8 Identity Protection

#### **Purpose**
Automated detection and remediation of identity-based risks

#### **Requires**: Microsoft Entra ID P2

#### **Risk Types**

**1. Sign-in Risk** (Real-time + Offline):
   - Anonymous IP address
   - Atypical travel
   - Malware-linked IP
   - Unfamiliar sign-in properties
   - Leaked credentials
   - Password spray attack
   - **Levels**: Low, Medium, High

**2. User Risk** (Offline):
   - Leaked credentials found on dark web
   - Anomalous user activity
   - **Levels**: Low, Medium, High

#### **Risk-Based Policies**
- **Sign-in Risk Policy**:
  - Require MFA for medium/high risk sign-ins
  - Block high-risk sign-ins
  
- **User Risk Policy**:
  - Require password change for high-risk users
  - Block high-risk users

#### **Remediation**
- **Self-remediation**: User completes MFA or password reset
- **Administrator remediation**: Admin confirms compromise or dismisses risk
- **Automated**: Risk-based Conditional Access policies

#### **Integration with Conditional Access**
- Use sign-in risk and user risk as **conditions** in CA policies
- Example: Require MFA if sign-in risk is Medium or High

---

### 2.9 Microsoft Entra External ID

#### **B2B (Business-to-Business)**
- **Purpose**: Collaborate with external partners/vendors
- **Guest Users**: External users invited to your tenant
- **Authentication**: User authenticates with their home organization
- **Access**: Grant access to apps/resources via Entra ID
- **Licensing**: Based on monthly active users (MAU)

#### **B2C (Business-to-Consumer)**
- **Purpose**: Customer-facing applications
- **Separate Tenant**: B2C tenant distinct from corporate Entra ID
- **Identity Providers**: 
  - Local accounts (username/password, email)
  - Social (Facebook, Google, Microsoft)
  - Enterprise (SAML, OIDC)
- **Custom Policies**: Full control over user journey
- **Licensing**: MAU-based pricing

---

### 2.10 Hybrid Identity and On-Premises Integration

#### **Active Directory Domain Services (AD DS)**
- **Use when**: You need full domain controller control on-prem or IaaS VMs (classic Windows auth, Group Policy, Kerberos/NTLM), custom forest/domain design, schema extensions.
- **Hosting**: On-prem physical/VM DCs or self-managed DCs on Azure IaaS.
- **Pros**: Full flexibility, trusts, fine-grained control; supports legacy apps.
- **Cons**: You manage patching, backup, DR, replication, capacity; higher ops overhead.

#### **Azure AD DS (managed domain)**
- **Use when**: You need domain join/LDAP/Kerberos/NTLM for legacy apps in Azure, but do **not** want to run/patch DCs.
- **Pros**: Microsoft manages DCs, patches, replication, backups; supports secure LDAP option.
- **Cons/Limitations**: No schema extension, limited admin roles (no Enterprise Admin), no custom FSMO control, single managed forest per instance, limited trust options.
- **Identity source**: Objects sync **one-way** from Entra ID to Azure AD DS; changes in managed domain do not write back.

#### **Entra Connect (sync from AD DS to Entra ID)**
- **Purpose**: Synchronize on-prem AD identities to Entra ID; enable SSO and hybrid access.
- **Top sync choices**:
   - **Password Hash Sync (PHS)**: Cloud auth, simplest, recommended default; hashes synced; supports Seamless SSO; lowest complexity.
   - **Pass-Through Authentication (PTA)**: On-prem agents validate passwords; use when you need on-prem policies during sign-in, but want to avoid full federation.
   - **Federation (AD FS)**: Use only when required (smartcards, custom MFA, complex auth policies); highest complexity and ops overhead.
- **High availability**: Use **staging server** or multiple PTA agents; monitor sync health.
- **Writeback options** (when enabled): Password writeback (self-service resets), group writeback (for Microsoft 365 groups), device writeback (for AD FS device recognition), selective attribute writeback as supported.

#### **Entra Connect Cloud Sync**
- **Lightweight agents** with cloud-based provisioning; use for multi-forest or when you want simpler agent footprint.
- **When to choose**: New deployments needing basic attribute sync, multi-forest to single tenant, or to reduce on-prem server dependency; does **not** support all writeback features (check needed capabilities).

#### **Choosing the right model**
- **PHS + Seamless SSO**: Default for most; lowest complexity.
- **PTA**: Need on-prem password validation or cannot store password hashes in cloud.
- **AD FS**: Only if mandated (smartcards, complex claim rules, third-party MFA not supported in cloud, strict regulatory needs).
- **Azure AD DS**: Need managed domain services for legacy apps in Azure without managing DCs.
- **Self-managed AD DS in Azure**: Need full DC control, schema changes, or trusts beyond Azure AD DS limits.

#### **Network and security basics**
- Ensure outbound connectivity from sync agents/PTA to Entra ID endpoints; allow required ports to domain controllers.
- For Azure AD DS, plan VNet integration, DNS updates for VMs, and consider secure LDAP over TLS if required.
- Implement tiered admin model and break-glass accounts for both Entra ID and AD DS.

---

## 3. Design Governance

### 3.1 Management Groups

#### **Purpose**
Organize subscriptions into hierarchy for unified policy and access management

#### **Hierarchy**
```
Root Management Group (Tenant Root)
  └─ Management Group (Level 1)
      └─ Management Group (Level 2)
          └─ Subscription
              └─ Resource Group
                  └─ Resource
```

#### **Key Facts**
- **Maximum depth**: 6 levels (excluding root and subscription)
- **Root Management Group**: Auto-created, one per tenant, cannot be deleted
- **All subscriptions** roll up to root by default
- Each subscription can have **only one parent** management group
- **Single tenant** support (10,000 management groups per tenant)

#### **Inheritance**
- **Azure Policy**: Policies assigned to MG apply to all child MGs/subscriptions/resources
- **Azure RBAC**: Role assignments inherit downward
- **Cannot be overridden** at lower levels (design carefully)

#### **Use Cases**
- Separate production, development, test environments
- Organize by department, geography, business unit
- Apply compliance policies (PCI, HIPAA, ISO) to specific groups
- Centralized billing management

#### **Best Practices**
- **Keep hierarchy flat**: 3-4 levels maximum (management overhead)
- **Don't duplicate org chart**: Design for governance, not mirroring org structure
- **Assign policies at MG level**: Enforce compliance across subscriptions
- **Use resource tags** for cross-cutting queries (not deep MG hierarchies)
- **Create "sandbox" MG**: Isolated experimentation environment
- **Platform MG**: Common policies for all platform resources

---

### 3.2 Azure Subscriptions

#### **Purpose**
Logical container for resources, billing boundary, scale boundary

#### **Design Considerations**

**1. Subscription Types**:
   - Enterprise Agreement (EA)
   - Microsoft Customer Agreement (MCA)
   - Cloud Solution Provider (CSP)
   - Pay-As-You-Go

**2. Limits**:
   - Resource limits per subscription (e.g., 980 resource groups per region)
   - API rate limits
   - **Use multiple subscriptions** to scale beyond limits

**3. Isolation Strategies**:
   - **Environment**: Dev, Test, Prod subscriptions
   - **Department/Business Unit**: Finance, HR, Engineering
   - **Compliance**: Separate regulated workloads
   - **Billing**: Chargeback to departments

#### **Best Practices**
- **Landing Zone model**: Separate subscriptions for connectivity, identity, management, workloads
- **Policy-driven governance**: Use Azure Policy to enforce standards
- **Cost management**: Tags + subscriptions for chargeback
- **RBAC**: Assign at subscription level sparingly (use resource groups)

---

### 3.3 Resource Groups

#### **Purpose**
Logical container for resources with shared lifecycle

#### **Key Characteristics**
- **Lifecycle management**: Delete RG = delete all resources
- **Regional**: Metadata stored in specific region
- **Access control**: RBAC assigned at RG level applies to all resources
- **Tags**: Applied to RG, optionally inherited by resources
- **Move resources**: Between RGs (with limitations)

#### **Design Principles**
- Group resources with **same lifecycle** (deploy/delete together)
- Group resources **managed by same team**
- Group resources for **same application/workload**
- Don't mix environments (dev/test/prod) in same RG

---

### 3.4 Resource Tagging

#### **Purpose**
Metadata for organizing, searching, managing, and cost allocation

#### **Tag Structure**
- **Name-Value pairs**: `Environment: Production`, `CostCenter: 12345`
- **Max 50 tags** per resource/resource group
- **Case sensitivity**: Tags are case-sensitive (Name and Value)

#### **Common Tag Categories**
1. **Functional**:
   - Environment (Dev, Test, Prod)
   - Application (SAP, CRM, HR)
   - Tier (Web, App, Data)

2. **Classification**:
   - Data Sensitivity (Public, Confidential, Restricted)
   - Compliance (PCI, HIPAA, ISO27001)

3. **Operational**:
   - Owner (email/team)
   - Criticality (Mission-critical, High, Medium, Low)
   - Backup (Daily, Weekly, None)
   - Monitoring (Enabled, Disabled)

4. **Financial**:
   - Cost Center (department code)
   - Business Unit
   - Project (project ID)
   - Approved By

#### **Enforcement with Azure Policy**
- **Add tag**: Automatically add tag if missing
- **Require tag**: Block creation without specific tag
- **Inherit tag**: Copy tag from resource group
- **Modify tag**: Change tag values

#### **Best Practices**
- **Define tagging strategy** upfront (governance requirement)
- **Enforce with Azure Policy** (consistent application)
- **Use for cost allocation**: Tag resources by cost center/project
- **Automate**: Tag via IaC templates (ARM, Bicep, Terraform)
- **Query with Azure Resource Graph**: Find resources by tags
- **Document**: Maintain tag taxonomy documentation

---

### 3.5 Azure Policy

#### **Purpose**
Define and enforce organizational standards and assess compliance at scale

#### **Core Concepts**

**1. Policy Definition**:
   - **Rule**: JSON definition of condition + effect
   - **Parameters**: Make definitions reusable
   - **Built-in**: 300+ provided by Microsoft
   - **Custom**: Create organization-specific policies

**2. Initiative (Policy Set)**:
   - **Collection** of policy definitions
   - Example: "ISO 27001", "PCI DSS 3.2.1", "Azure Security Benchmark"
   - Simplifies assignment and compliance tracking

**3. Assignment**:
   - Apply policy/initiative to **scope** (management group, subscription, resource group)
   - **Exclusions**: Exclude specific subscriptions/resource groups/resources
   - **Parameters**: Provide values for policy parameters

**4. Exemption**:
   - **Waiver**: Temporary or permanent exception from policy
   - **Mitigated**: Addressed through alternative control
   - Requires justification
   - Scoped to specific resources

#### **Policy Effects** (Exam-Critical)

**1. Audit**:
   - **Evaluate** resource compliance
   - **Create warning** in activity log if non-compliant
   - **Doesn't block** resource creation/update
   - Use for: Monitoring, reporting

**2. Deny**:
   - **Block** non-compliant resource creation/update
   - **Returns error** to user
   - Use for: Enforce governance requirements

**3. Append**:
   - **Add properties** to resource during creation/update
   - Example: Add required tags, force specific settings
   - Use for: Enforce defaults

**4. Modify**:
   - **Add, update, or remove** properties on resource
   - Can **remediate existing resources** (remediation task)
   - More flexible than Append
   - Requires **managed identity** for policy assignment
   - Use for: Tag management, enforce settings

**5. DeployIfNotExists**:
   - **Deploy additional resources** if condition not met
   - Example: Deploy diagnostic settings, antimalware extension
   - **Remediation available** for existing resources
   - Requires **managed identity**
   - Use for: Automated compliance

**6. AuditIfNotExists**:
   - **Audit** if related resource doesn't exist
   - Example: Check if VM has antimalware extension
   - No blocking, only reporting
   - Use for: Monitoring dependencies

**7. Disabled**:
   - Policy definition exists but not enforced
   - Use for: Testing, temporary disablement

**8. Manual**:
   - Requires **manual attestation** of compliance
   - Use for: Non-automatable requirements

#### **Evaluation**
- **Trigger Events**:
  - Resource creation/update (15 minutes for compliance state)
  - Policy assignment created/updated
  - **Standard evaluation**: Every 24 hours
  
- **Compliance States**:
  - Compliant
  - Non-compliant
  - Conflicting (multiple policies with contradictory effects)
  - Not started
  - Exempt

#### **Remediation**
- **Remediation Task**: Apply `Modify` or `DeployIfNotExists` to existing non-compliant resources
- **Managed Identity**: Required for remediation tasks
- **Bulk remediation**: Remediate at scale across scope

#### **Best Practices**
- **Start with Audit**: Understand impact before enforcing Deny
- **Use initiatives**: Group related policies
- **Assign at highest scope**: Management group level for org-wide policies
- **Use built-in policies**: Leverage Microsoft-provided definitions
- **Test in non-production**: Validate before prod deployment
- **Exemptions**: Document and review periodically
- **Monitor compliance**: Azure Policy Compliance dashboard
- **Automate remediation**: Use DeployIfNotExists/Modify with remediation tasks

#### **Common Policy Scenarios**
- **Enforce resource locations**: Allow only specific Azure regions
- **Require tags**: Mandate cost center tag on all resources
- **Enforce naming conventions**: Resource name patterns
- **Require encryption**: Enforce TDE on SQL databases
- **Restrict VM SKUs**: Allow only approved VM sizes
- **Enforce backup**: Ensure VMs have backup configured
- **Diagnostic settings**: Automatically deploy logging

---

### 3.6 Resource Locks

#### **Purpose**
Prevent accidental deletion or modification of critical resources

#### **Lock Types**

**1. CanNotDelete**:
   - **Read and modify** allowed
   - **Delete blocked**
   - Use for: Protect production resources from accidental deletion

**2. ReadOnly**:
   - **Read allowed**
   - **Modify and delete blocked**
   - Blocks all write operations (including control plane operations like scaling)
   - Use for: Freeze configuration (e.g., during audit)

#### **Lock Scope**
- Subscription
- Resource Group (applies to all resources within)
- Resource

#### **Inheritance**
- Locks **inherit downward**
- Resource group lock applies to all contained resources
- Child locks can be more restrictive, not less

#### **Permissions**
- **Create/Delete locks**: Requires `Microsoft.Authorization/locks/*` permission
- **Owner** and **User Access Administrator** roles have lock management

#### **Best Practices**
- Apply **CanNotDelete** to production resource groups
- Use **ReadOnly** temporarily (freeze during changes)
- Document lock rationale
- Combine with **RBAC** for defense in depth
- **Delete lock** blocks ARM operations (e.g., App Service plan scaling)

---

### 3.7 Azure Blueprints

#### **Purpose**
Declarative way to orchestrate deployment of resource templates, policies, role assignments, and resource groups

#### **Components**
1. **Artifacts**:
   - ARM templates
   - Policy assignments
   - Role assignments
   - Resource groups

2. **Versioning**: Track blueprint changes
3. **Locking**: Prevent modification of deployed resources

#### **Lifecycle**
1. **Define**: Create blueprint with artifacts
2. **Publish**: Versioned blueprint available for assignment
3. **Assign**: Deploy to subscription
4. **Update**: Publish new version, reassign

#### **Use Cases**
- **Repeatable deployments**: Deploy compliant landing zones
- **Governance enforcement**: Policies + roles + resources
- **Audit compliance**: Track blueprint assignments

#### **Best Practices**
- Use for **repeatable subscription setup**
- Version blueprints for change tracking
- Apply to **new subscriptions** in management group

---

## 📝 Quick Reference Tables

### **Authentication vs. Authorization**

| Aspect | Authentication | Authorization |
|--------|---------------|--------------|
| **Definition** | Verify identity (who you are) | Verify permissions (what you can do) |
| **Azure Service** | Microsoft Entra ID | Azure RBAC, Conditional Access |
| **Mechanisms** | Password, MFA, certificates, tokens | Roles, policies, conditions |

---

### **RBAC Scope Inheritance**

| Scope | Role Assignment | Inherited By |
|-------|----------------|-------------|
| Management Group | Reader | All child MGs, subscriptions, RGs, resources |
| Subscription | Contributor | All resource groups and resources in subscription |
| Resource Group | Owner | All resources in RG |
| Resource | VM Contributor | Only that specific resource |

---

### **Azure Policy Effects Comparison**

| Effect | Blocks Creation | Modifies Resource | Remediates Existing | Use Case |
|--------|----------------|-------------------|---------------------|----------|
| **Audit** | ❌ | ❌ | ❌ | Reporting only |
| **Deny** | ✅ | ❌ | ❌ | Enforce compliance (prevent) |
| **Append** | ❌ | ✅ | ❌ | Add properties (create/update) |
| **Modify** | ❌ | ✅ | ✅ | Change properties + remediate |
| **DeployIfNotExists** | ❌ | ➕ Creates | ✅ | Auto-deploy resources |
| **AuditIfNotExists** | ❌ | ❌ | ❌ | Check related resources |

---

### **Diagnostic Settings Destinations**

| Destination | Use Case | Retention | Cost |
|------------|----------|-----------|------|
| **Log Analytics** | Query, analyze, correlate, alerts | 30-730 days | $$$ (per GB ingested) |
| **Storage Account** | Long-term archive, compliance | Unlimited | $ (storage cost) |
| **Event Hubs** | Stream to SIEM, external tools | Real-time | $$ (throughput units) |
| **Partner** | Datadog, Splunk, etc. | Varies | Varies |

---

## 🎯 Exam Tips

### **Identity & Access**
1. **Managed Identities > Service Principals** - Always prefer MI when available
2. **MFA is mandatory** for privileged accounts (Conditional Access)
3. **PIM** reduces risk - Eligible assignments over active
4. **Key Vault RBAC** is the modern approach (not access policies)
5. **Separate duties**: Azure RBAC (resources) vs. Entra ID Roles (directory)

### **Monitoring**
1. **Diagnostic settings** are NOT enabled by default - must configure
2. **Activity log** is subscription-level, **Resource logs** are resource-specific
3. **Log Analytics** = centralized + KQL querying
4. **Application Insights** workspace-based is recommended over classic

### **Governance**
1. **Management groups** max depth = 6 levels
2. **Azure Policy** assignment can be at MG, subscription, or RG level
3. **Modify/DeployIfNotExists** require managed identity
4. **Start with Audit**, then move to Deny
5. **Resource locks** prevent deletion/modification - Owner role can manage
6. **Tags** don't inherit by default (use Azure Policy to enforce inheritance)

### **Compliance**
1. **Initiatives** = grouped policy definitions (use for compliance frameworks)
2. **Exemptions** require justification
3. **Remediation tasks** for existing non-compliant resources
4. **Blueprints** for repeatable deployments with governance

---

## 📚 Additional Study Resources

### **Official Microsoft Learn Paths**
- [AZ-305: Design identity, governance, and monitor solutions](https://learn.microsoft.com/en-us/training/paths/design-identity-governance-monitor-solutions/)
- [Design authentication and authorization solutions](https://learn.microsoft.com/en-us/training/modules/design-authentication-authorization-solutions/)
- [Design governance](https://learn.microsoft.com/en-us/training/modules/design-governance/)
- [Design a solution to log and monitor Azure resources](https://learn.microsoft.com/en-us/training/modules/design-solution-to-log-monitor-azure-resources/)

### **Key Documentation**
- [Azure Policy documentation](https://learn.microsoft.com/en-us/azure/governance/policy/)
- [Azure RBAC documentation](https://learn.microsoft.com/en-us/azure/role-based-access-control/)
- [Microsoft Entra ID documentation](https://learn.microsoft.com/en-us/entra/identity/)
- [Azure Monitor documentation](https://learn.microsoft.com/en-us/azure/azure-monitor/)
- [Azure Key Vault documentation](https://learn.microsoft.com/en-us/azure/key-vault/)

---

**Last Updated:** January 2026  
**Based on:** AZ-305 Exam Skills Measured (October 18, 2024 version)
