---
post_title: "AZ-305: Design Business Continuity Solutions"
author1: "GitHub Copilot"
post_slug: "az-305-design-business-continuity-solutions"
microsoft_alias: "copilot"
featured_image: "/images/azure-default.png"
categories: ["Azure"]
tags: ["AZ-305", "business continuity", "disaster recovery", "high availability", "Azure"]
ai_note: "Content generated with AI assistance based on official Microsoft documentation."
summary: "Concise study guide for AZ-305 business continuity objectives covering HA, DR, backup, and resilience patterns."
post_date: "2026-01-27"
---

## Design Business Continuity Solutions (15-20%)

### Core Concepts
- Define **RPO** (data loss window) and **RTO** (time to restore); map workloads to targets.
- Use **region pairs** for DR; prioritize **availability zones** for intra-region resiliency.
- Prefer **zone-redundant services** (e.g., App Service zone redundancy, zone-redundant storage tiers, SQL Business Critical ZRS).
- Align SLAs: load balancers + zone redundancy improve composite availability.

### Compute and App Layer
- **Azure Load Balancer** for L4 HA; **Application Gateway** (WAF) for HTTP(S); **Front Door** for global routing/acceleration; **Traffic Manager** for DNS-based failover.
- **VM availability sets** (fault + update domains); **VM scale sets** with zones for autoscale and distribution.
- **AKS**: multiple node pools across zones; use **pod disruption budgets** and **readiness probes**; enable **cluster autoscaler**.
- **App Service**: zone redundancy (Premium v3/Isolated v2), deployment slots for staged releases; deploy to paired region for active-passive/active-active.

### Data Layer
- **Azure SQL Database**: geo-replication for manual failover; **auto-failover groups** for coordinated cross-region failover; built-in PITR and optional LTR.
- **SQL Managed Instance**: failover groups across regions; backup-based restore; consider latency for cross-region.
- **SQL Server on VMs**: Always On AGs across zones/regions; use Azure Load Balancer for listener; manage patching/FSW.
- **Cosmos DB**: multi-region replication; **multi-region writes** for active-active; choose consistency level to balance latency and RPO (bounded staleness/strong vs session/eventual).
- **Storage**: choose redundancy based on DR need: **LRS/ZRS** (local/zone), **GRS/GZRS**, **RA-GRS/RA-GZRS** for read access during outage.

### Backup and Recovery
- **Azure Backup** vaults for VMs, SQL in Azure VMs, and files; enable **soft delete** and **immutable vault** for ransomware protection; configure retention per policy.
- **Application-consistent backups** via extensions for VMs and pre/post scripts.
- **SQL PITR** defaults (7-35 days); **LTR** up to 10 years in Blob; **Geo-restore** uses geo-replicated backups when region unavailable.
- **Cosmos DB** continuous backup for point-in-time restore; periodic backups every 4 hours (configurable per account).

### Disaster Recovery and Orchestration
- **Azure Site Recovery (ASR)** for orchestrated failover/failback of VMs (Azure-to-Azure or on-premises-to-Azure); supports test failovers and recovery plans with runbooks.
- Prioritize **paired regions** and **Platform-supported replication** (e.g., SQL auto-failover groups, Cosmos multi-region) before ASR where available.
- Regularly test DR plans (non-disruptive test failover) and document runbooks with clear owners and contact paths.

### Monitoring and Validation
- **Azure Monitor**: alerts on health probes, replica lag, backup success, and failover events; use **Log Analytics** for trend analysis.
- Track and review DR drills; measure against RPO/RTO; update capacity reservations/budgets for secondary regions.

## Practice Questions

1. A mission-critical API must survive a zone outage with no data loss beyond five minutes and fail over automatically within the region. Which design fits best?
   - A. Single VM with managed disks
   - B. VM scale set across zones behind a Standard Load Balancer
   - C. Availability set with Basic Load Balancer
   - D. Single App Service in one zone
2. You need cross-region failover for Azure SQL Database with minimal admin effort and a single failover endpoint. What should you choose?
   - A. Geo-replication with manual DNS change
   - B. Auto-failover group
   - C. Transactional replication
   - D. Backup/restore to secondary region on demand
3. An AKS workload must keep serving during planned maintenance and zone failures. What is the best approach?
   - A. Single node pool in one zone
   - B. Multiple node pools spread across zones with pod disruption budgets
   - C. Horizontal Pod Autoscaler only
   - D. Single node pool with max surge 0
4. A storage account must remain readable during a regional outage without initiating failover. Which redundancy tier satisfies this?
   - A. LRS
   - B. ZRS
   - C. GRS
   - D. RA-GZRS
5. An on-premises VMware app needs orchestrated DR to Azure with test failovers and planned failback. Which service should you use?
   - A. Azure Backup
   - B. Azure Site Recovery
   - C. Storage Explorer
   - D. Traffic Manager
6. Compliance requires seven-year retention and protection from ransomware for SQL Server on Azure VM backups. Which configuration is best?
   - A. File copy to Blob Cool tier
   - B. Azure Backup vault with soft delete and immutable vault
   - C. Local disk snapshots only
   - D. Manual .bak uploads weekly
7. A global Cosmos DB app needs active-active writes with minimal write latency and bounded RPO. Which setup fits?
   - A. Single-region Cosmos DB with Session consistency
   - B. Multi-region writes enabled with Session or Bounded Staleness
   - C. Multi-master SQL Server on VMs
   - D. Cosmos DB single-master with Strong consistency
8. A line-of-business app on App Service must fail over to a paired region while keeping TLS certificates in sync. What should you implement?
   - A. Deploy to both regions with Front Door for health-based failover and Key Vault-backed certs
   - B. Single region with backups
   - C. Traffic Manager without secondary deployment
   - D. Swap slots in same region
9. During a DR drill you need to ensure SQL Managed Instance data loss stays under 15 minutes during regional failover. Which feature provides this?
   - A. Log shipping
   - B. Auto-failover group for Managed Instance
   - C. Bacpac import/export
   - D. Transactional replication
10. A backup admin needs to prevent accidental deletion of recovery points. Which control should be applied first?
    - A. Disable backups
    - B. Enable soft delete on the vault
    - C. Remove role assignments
    - D. Delete old vaults

## Answer Key

- 1: B
- 2: B
- 3: B
- 4: D
- 5: B
- 6: B
- 7: B
- 8: A
- 9: B
- 10: B
