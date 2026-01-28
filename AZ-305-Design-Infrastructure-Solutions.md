---
post_title: "AZ-305: Design Infrastructure Solutions"
author1: "GitHub Copilot"
post_slug: "az-305-design-infrastructure-solutions"
microsoft_alias: "copilot"
featured_image: "/images/azure-default.png"
categories: ["Azure"]
tags: ["AZ-305", "infrastructure", "compute", "networking", "Azure"]
ai_note: "Content generated with AI assistance based on official Microsoft documentation."
summary: "Study guide for AZ-305 infrastructure design objectives across compute, containers, networking, and security."
post_date: "2026-01-27"
---

## Design Infrastructure Solutions (30-35%)

### Compute and Container Platform Choices
- **App Service** for managed web APIs; use deployment slots and autoscale; choose Premium v3/Isolated for VNet integration and zone redundancy.
- **AKS** for container orchestration; use node pools per workload/zone; enable cluster autoscaler and pod disruption budgets; Azure CNI for VNet IPs; use Uptime SLA for control plane.
- **Azure Functions** for event-driven bursty workloads; Premium or Dedicated for VNet and cold-start avoidance.
- **VMs and VM Scale Sets** when OS control is required; use availability sets or zones; choose managed disks (Premium SSD v2/Ultra for low latency) and proximity placement groups for latency-sensitive tiers.

### Storage and Data Placement (in Infra Context)
- Select **managed disk type** based on performance: Standard HDD (dev/test), Standard SSD (balanced), Premium SSD v2 or Ultra (IOPS/latency sensitive); enable disk encryption (platform-managed or customer-managed keys).
- Use **ephemeral OS disks** for stateless scale sets to speed up reimages and reduce cost.
- Place stateful tiers in the same zone/proximity placement group to reduce latency; use **availability zones** for HA.

### Networking and Connectivity
- **VNet design**: plan IP addressing, subnets by tier, and growth; use **hub-and-spoke** for shared services and isolation; prefer **VNet peering** for low-latency intra-Azure connectivity.
- **Hybrid connectivity**: **ExpressRoute** for private, high-bandwidth SLA-backed links; **VPN Gateway** for quick setup or backup to ExpressRoute; support active-active and zone-redundant gateways.
- **Load balancing**: **Front Door** for global HTTP(S) routing/acceleration and WAF; **Application Gateway** for regional L7 with WAF; **Standard Load Balancer** for L4; use health probes and zone redundancy.
- **Private access**: **Private Link/Private Endpoints** to consume PaaS privately; **Service Endpoints** as fallback when Private Link unavailable.

### Security and Governance
- Segment with **Network Security Groups** and **Application Security Groups** for workload-based rules; use **Azure Firewall** or **Firewall Premium** for centralized egress control and TLS inspection.
- Enable **DDoS Protection Standard** for production VNets with public endpoints.
- Use **Managed Identities** for compute to access Azure resources; store secrets in **Key Vault** with RBAC and soft delete/purge protection.
- Enforce **policy** and **blueprints/landing zones** for consistent guardrails (naming, tagging, allowed SKUs, region restrictions).

### Management, Observability, and Cost
- **VM insights**, **Container insights**, and **Log Analytics** for telemetry; **Azure Monitor alerts** for autoscale and availability signals; enable **Activity Log** alerts for change tracking.
- **Autoscale** on App Service/AKS/VMSS based on CPU, requests, queue length, or custom metrics; set minimum/maximum bounds to control spend.
- Use **Azure Advisor** and **Cost Management** budgets/alerts; schedule start/stop for non-prod VMs; leverage **reserved instances** or **Savings Plans** for steady-state compute.

## Practice Questions

1. A web API needs quick deployment, built-in CI/CD slots, and VNet integration with minimal ops. Which service fits best?
   - A. Azure VM scale set
   - B. Azure App Service Premium v3
   - C. Azure Functions Consumption
   - D. Azure Kubernetes Service
2. A multi-tenant container platform needs per-workload isolation, zone resiliency, and fast autoscale. Which configuration is best?
   - A. Single AKS node pool in one zone
   - B. AKS with multiple node pools across zones and cluster autoscaler
   - C. App Service Free tier
   - D. Single VM behind Basic Load Balancer
3. You must provide private, SLA-backed connectivity (5 9s) between on-premises and Azure for ERP workloads. What should you choose?
   - A. Site-to-site VPN only
   - B. ExpressRoute
   - C. Point-to-site VPN
   - D. Public internet with WAF
4. A stateless microservice needs fast reimage times and lowest possible OS disk cost on VM scale sets. What should you use?
   - A. Premium SSD OS disks
   - B. Ephemeral OS disks
   - C. Standard HDD data disks
   - D. Ultra disks
5. You need centralized outbound filtering, TLS inspection, and FQDN-based rules for all spokes. Which option fits?
   - A. Network Security Groups only
   - B. Azure Firewall Premium in hub with forced tunneling
   - C. Application Gateway WAF only
   - D. Basic Load Balancer
6. A data-sensitive API must be reachable privately from spoke VNets without internet exposure. What should you implement?
   - A. Public IP on App Service
   - B. Private Endpoint to the service and VNet integration on callers
   - C. Service Endpoint only
   - D. NAT Gateway
7. A production VNet hosts public endpoints and must mitigate volumetric attacks. What control should be added?
   - A. DDoS Protection Standard on the VNet
   - B. Only NSGs
   - C. Only Azure Firewall
   - D. No action needed
8. To reduce cost on a steady-state VMSS workload while preserving scale capability, what purchasing option is best?
   - A. Pay-as-you-go only
   - B. 1/3-year reserved instances or Savings Plans for baseline, with PAYG for burst
   - C. Spot only
   - D. Dev/test subscription
9. A solution must route global users to the nearest healthy region with SSL offload and caching. Which service should front the app?
   - A. Traffic Manager only
   - B. Azure Front Door
   - C. Basic Load Balancer
   - D. Application Gateway only
10. Which configuration provides lower latency between a web tier and database tier within the same region?
    - A. Different regions with VNet peering
    - B. Same zone using proximity placement groups
    - C. Same region but different zones without PPG
    - D. Public IP communication

## Answer Key

- 1: B
- 2: B
- 3: B
- 4: B
- 5: B
- 6: B
- 7: A
- 8: B
- 9: B
- 10: B
