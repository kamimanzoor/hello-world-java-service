# AZ-305 Exam Preparation: Design Data Storage Solutions (20-25%)

> **Source:** Official Microsoft Learn Documentation  
> **Last Updated:** January 2026  
> **Exam Weight:** 20-25% of total exam

---

## 📋 Table of Contents
1. [Design Data Storage Solutions for Relational Data](#1-design-data-storage-solutions-for-relational-data)
2. [Design Data Storage Solutions for Semi-Structured and Unstructured Data](#2-design-data-storage-solutions-for-semi-structured-and-unstructured-data)
3. [Design Data Integration Solutions](#3-design-data-integration-solutions)
4. [Data Protection and Redundancy](#4-data-protection-and-redundancy)

---

## 1. Design Data Storage Solutions for Relational Data

### 1.1 Azure SQL Database

#### **When to Use**
- Cloud-native applications and SaaS (Software-as-a-Service) platforms
- Need fully managed, auto-scaling relational database
- Want latest SQL Server features automatically
- Need single database or multiple databases with variable workloads

#### **Deployment Models**
1. **Single Database**: Isolated, dedicated resources; easy to scale independently
2. **Elastic Pools**: Shared resources across multiple databases; cost-efficient when usage patterns vary
   - **Use elastic pools when**: Database utilization differs; peak loads don't align; want predictable budgets
   - **Cost benefit**: eDTUs shared among many databases = fewer total eDTUs needed vs. single databases

#### **Purchasing Models**

**DTU-Based (Bundled - Simpler)**:
- Preconfigured bundles of compute, storage, I/O
- **Service Tiers**: Basic (light), Standard (general), Premium (high performance)
- **Best for**: Simple workloads, customers preferring bundled options
- **Cost**: All-inclusive per DTU tier

**vCore-Based (Flexible - Granular Control)**:
- **Service Tiers**:
  - **General Purpose**: Budget-balanced; remote premium storage; 2-128 vCores; 1-4 TB storage
  - **Business Critical**: High resilience; local SSD storage; 2-128 vCores; 1-4 TB storage; In-Memory OLTP available
  - **Hyperscale**: Massive scale; decoupled storage; 10-128 TB storage; rapid scaling; 2-128 vCores
- **Compute Tiers**: 
  - **Provisioned**: Fixed resources always allocated
  - **Serverless**: Auto-scales based on workload (0-max vCores); pauses during inactivity; lower cost for intermittent workloads
- **Best for**: Complex requirements, need fine-grained control, want Azure Hybrid Benefit

#### **Scalability Recommendations**
- **Scale UP**: Change service tier or compute size (minimal downtime)
- **Scale OUT**: Use elastic pools to distribute load across databases OR sharding (horizontal partitioning) for massive scale
- **Hyperscale tier**: Auto-scaling, instant snapshot-based backups, rapid restore (ideal for huge databases)

#### **High Availability**
- **Built-in HA**: Always-On replicas; local redundancy in primary; geo-replication available
- **Geo-Replication**: Replicate to up to 4 secondary regions; secondary replicas are **read-only** until failover
- **Auto-Failover Groups**: Automated failover; transparent DNS; supports all databases in a group to single secondary
- **Backup**: Automatic full (weekly), differential (daily), transaction logs (every 5 mins); PITR (Point-In-Time Restore)

---

### 1.2 Azure SQL Managed Instance

#### **When to Use**
- **Lift-and-shift migrations** from on-premises SQL Server
- Need **near-complete SQL Server compatibility** (features, T-SQL, CLR, linked servers)
- Require Windows authentication, Active Directory integration, or on-premises compatibility
- Complex SQL Server features not supported in SQL Database

#### **Key Differences from SQL Database**
- **Managed instance** = full SQL Server environment in cloud
- Supports SQL Server agent jobs, instance-level objects, multiple databases per instance
- Supports VNet integration (custom network setup)
- **Cost**: Higher than SQL Database, lower than self-managed SQL Server on VMs

#### **Considerations**
- Slightly higher latency than single database (managed instance = network isolation)
- Requires VNet/subnet planning
- Limited to same region for failover (use geo-replicated backups for DR)

---

### 1.3 SQL Server on Azure Virtual Machines

#### **When to Use**
- **Full control** over SQL Server (versions, patches, features, registry)
- Custom integrations (linked servers, custom extensions)
- Performance-critical workloads needing direct hardware access
- Compliance requirements preventing managed services

#### **Considerations**
- **You manage**: Patching, backups, high availability, disaster recovery, capacity
- **Higher ops overhead** than managed options
- **License costs**: Bring Your Own License (BYOL) or Azure-included licensing (higher VM cost)
- **Backups**: Use Azure Backup or SQL Server native tools; long-term retention in blob storage

---

### 1.4 Azure Cosmos DB (for Relational/NoSQL)

#### **When to Use - for Relational Scenarios**
- Multi-model (table API, SQL API, MongoDB, Cassandra, Gremlin)
- Global distribution with **multi-region writes** (active-active)
- Sub-10ms latency requirement anywhere globally
- No complex JOINs or transactions across documents
- IoT, real-time personalization, global content delivery

#### **Pricing Model - Request Units (RUs)**
- **Throughput charged**: RU/s (Request Units per second)
- **Operations cost**:
  - 1 KB read = 1 RU
  - 1 KB write = 5 RUs
  - Queries cost more based on complexity
- **Multi-region**: RUs provisioned replicate to ALL regions (if 10,000 RU/s in 2 regions = 20,000 RU/s cost)

#### **Consistency Levels** (Trade-off: Strong=Higher latency, Eventual=Lower latency)
- **Strong**: Highest guarantee; local minority quorum (reads cost 2x more)
- **Bounded staleness**: Bounded time lag (K versions or T time)
- **Session** (Default): Read-your-writes within session; single-replica reads
- **Consistent prefix**: Updates visible in order
- **Eventual**: Lowest cost; highest throughput; highest latency
- **Rule**: Weaker consistency = lower RU cost for reads

---

### 1.5 Azure Database for PostgreSQL / MySQL

#### **When to Use**
- Open-source relational databases (PostgreSQL, MySQL)
- Managed service (auto-backup, patching, HA)
- Not locked into SQL Server ecosystem
- Cost-effective for standard workloads

#### **Deployment**
- **Single Server** (Basic, General Purpose, Memory Optimized tiers)
- **Flexible Server** (recommended; better HA, VNet support)

---

## 2. Design Data Storage Solutions for Semi-Structured and Unstructured Data

### 2.1 Azure Blob Storage

#### **Purpose**
- Store massive quantities of unstructured data (files, media, logs, backups)
- **Not hierarchical** (flat namespace; pseudo-folders via naming)
- Blob types: Block (default), Page (VMs), Append (logs)

#### **Access Tiers** (Choose based on **access frequency** and **retention time**)

| Tier | Latency | Min Retention | Storage Cost | Access Cost | Use Case |
|------|---------|----------------|-------------|------------|----------|
| **Hot** | Milliseconds | N/A | Highest | Lowest | Frequent access (active data) |
| **Cool** | Milliseconds | 30 days | Lower | Higher | Infrequent access (backup 1-30 days) |
| **Cold** | Milliseconds | 90 days | Even lower | Even higher | Rare access (backup 30-90 days) |
| **Archive** | Hours (rehydrate) | 180 days | Lowest | Highest | Compliance/audit (backup 90+ days) |
| **Smart** | Auto-optimized | N/A | Optimized | Optimized | Auto-moves based on usage patterns |

**Exam Tip**: Lifecycle policies can **automatically move** blobs between tiers based on age/last-access time.

#### **Redundancy Options** (Protects against outages/disasters)
- **LRS** (Locally Redundant): 1 datacenter; 99.999999999% (11 9s) durability; lowest cost; risky if data center fails
- **ZRS** (Zone-Redundant): 3+ availability zones in one region; 99.9999999999% (12 9s); cross-zone protection
- **GRS** (Geo-Redundant): LRS in primary + async to secondary region; 99.99999999999999% (16 9s); RPO ≤15 min
- **GZRS** (Geo-Zone-Redundant): ZRS primary + async to secondary; 99.99999999999999% (16 9s); best overall
- **RA-GRS / RA-GZRS**: Read-access versions (can read secondary if primary fails)

**Decision**:
- **High availability needed**: Use **ZRS** or **GZRS** (local redundancy)
- **Disaster recovery needed**: Use **GRS/GZRS** (geographic replication)
- **Can tolerate data center loss**: Use **LRS** (cost-optimized)
- **Need read during regional outage**: Use **RA-GRS / RA-GZRS**

---

### 2.2 Azure Data Lake Storage Gen2 (ADLS Gen2)

#### **Purpose**
- Hadoop-compatible, Big Data analytics
- **Hierarchical namespace** (true folders/directories), unlike standard Blob Storage
- POSIX-compliant permissions
- **Optimized for**: Spark, Hadoop, analytics workloads

#### **Key Differences from Blob**
- True directory structure (not pseudo-folders)
- Better performance for directory operations
- Compatible with Spark, Hive, and big-data tools
- Same redundancy/tiering options as Blob Storage

#### **Backup & Protection**
- **Soft Delete**: Restore deleted files within retention period
- **Immutable storage**: Legal hold or time-locked retention (prevent modification)
- **Azure Backup vaulted backup**: Snapshot-based backup with object replication (new feature)
- **Point-in-time restore**: Via snapshots/versioning

---

### 2.3 Azure Table Storage

#### **When to Use**
- Simple key-value pairs with flexible schema
- Massive scale (billions of entities)
- Lower cost than relational DB for simple queries
- IoT sensor data, logging, reference data

#### **Structure**
- PartitionKey + RowKey = unique identifier
- Design for queries (partition key = major filter)

#### **Limitations**
- No complex joins or aggregations
- Max entity size: 1 MB
- No ACID transactions across partitions

#### **Modern Alternative**
- **Azure Cosmos DB for Table API**: Better performance, global distribution, higher throughput

---

### 2.4 Azure Cosmos DB

#### **When to Use - for Semi-Structured**
- JSON documents with varying schemas
- Global multi-region distribution
- High throughput, predictable latency (<10ms)
- Real-time personalization, catalogs, user profiles

#### **APIs Available**
- **NoSQL (SQL API)**: Document queries with SQL-like syntax
- **MongoDB**: Drop-in replacement for MongoDB apps
- **Cassandra**: Cassandra protocol compatibility
- **Gremlin**: Graph database queries
- **Table**: Key-value like Azure Table Storage

#### **Consistency Trade-offs** (See section 1.4 for details)
- Strong/Bounded staleness: 2x RU cost for reads
- Session/Consistent prefix/Eventual: Standard RU cost

---

## 3. Design Data Integration Solutions

### 3.1 Azure Data Factory

#### **Purpose**
- **Cloud-based ETL/ELT service** for data integration at scale
- **Code-free or low-code** pipeline design (visual UI)
- Connect to 200+ data sources (on-prem, cloud, SaaS)

#### **Key Concepts**
- **Pipelines**: Workflows that orchestrate activities
- **Activities**: Copy data, transform, execute notebooks/stored procedures
- **Linked Services**: Connections to data sources (credentials, connection strings)
- **Datasets**: Data source/sink definitions (schema, format)
- **Integration Runtimes**:
  - **Azure IR**: Cloud execution; data within Azure
  - **Self-Hosted IR**: On-premises/private network; install agent locally
  - **Azure-SSIS IR**: Run SQL Server Integration Services packages

#### **ETL vs ELT**
- **ETL**: Extract → Transform (on-prem/staging) → Load
- **ELT**: Extract → Load (to cloud) → Transform (in cloud, e.g., Synapse)
- **Data Factory supports both**; ELT scales better for massive data

#### **Data Wrangling**
- Citizen data integrators (non-coders) prepare data visually
- Power Query M-code generation; executed as Spark jobs
- Similar to Excel/Power BI dataflows but at cloud scale

#### **Common Patterns**
- **Hybrid integration**: On-premises DB → Cloud Data Lake → Synapse → BI
- **Data movement**: Bulk copy, incremental loads
- **Data transformation**: Mapping data flows (visual Spark jobs)
- **Monitoring**: Built-in logging, pipeline runs, data lineage

---

### 3.2 Azure Synapse Analytics

#### **Purpose**
- **Modern data warehouse** (formerly SQL Data Warehouse)
- **Unified analytics platform**: Integrate data, explore, transform, analyze
- **Massively Parallel Processing (MPP)** architecture

#### **Components**
1. **Synapse SQL Pools** (formerly DW):
   - **Dedicated SQL Pool**: Provisioned compute; best for structured queries
   - **Serverless SQL Pool**: Pay-per-query; on-demand without provisioning
2. **Apache Spark Pool**: Distributed processing; Scala, Python, SQL
3. **Pipelines**: Built-in Data Factory capabilities
4. **Data Studio**: Integrated notebooks, dashboards, source control

#### **Integration Pattern**
- **Data Lake (ADLS Gen2)** → **Data Factory** (ETL) → **Synapse SQL/Spark** → **BI Tools (Power BI)**

#### **PolyBase / COPY Statement**
- **PolyBase**: Efficient parallel data loading into Synapse SQL
- **Staged copy**: If source format incompatible, Data Factory stages in Blob, then PolyBase loads
- **COPY statement**: T-SQL based; alternative to PolyBase; native support for Parquet, ORC
- **Performance**: PolyBase/COPY = 100-1000x faster than bulk insert

---

## 4. Data Protection and Redundancy

### 4.1 Storage Redundancy

#### **Primary Region Redundancy**
- **LRS**: Single datacenter; 3 copies; cheapest; risky
- **ZRS**: 3+ zones in one region; high availability; middle cost
- **Recommendation**: Use ZRS for data lakes and analytics (high availability needs)

#### **Secondary Region Redundancy**
- **GRS**: LRS primary + async GRS secondary; RPO ≤15 min; read-only until failover
- **GZRS**: ZRS primary + async LRS secondary; maximum protection
- **RA-GRS / RA-GZRS**: Read-access (read from secondary without failover)
- **RPO** (Recovery Point Objective): Time lag between primary and secondary writes (typically ≤15 min)

#### **Decision Tree**
```
Need disaster recovery? 
├─ Yes, need read during outage → Use RA-GRS or RA-GZRS
├─ Yes, failover acceptable → Use GRS or GZRS
└─ No, local only → Use LRS or ZRS
```

---

### 4.2 Backup & Recovery

#### **Azure Storage Data Protection Options**

**For Storage Accounts**:
- **ARM Locks**: Prevent deletion (CanNotDelete or ReadOnly)
- **Soft Delete**: Recover deleted containers/blobs within retention (7-90 days recommended)
- **Blob Versioning**: Auto-save previous versions on overwrite
- **Blob Snapshots**: Manual point-in-time copies (retained if blob deleted)
- **Immutability Policies**: WORM (write-once-read-many); legal hold or time-based retention

**For Critical Data**:
- **Vaulted Backup** (ADLS Gen2): Snapshot + object replication to backup vault
  - Long-term retention (10 years)
  - Ransomware protection
  - Recovery point every 15 minutes

#### **Encryption**
- **At-rest**: Azure Storage Service Encryption (ASSE) with Microsoft-managed keys (default)
- **Customer-managed keys**: Store in Azure Key Vault for extra control
- **In-transit**: TLS 1.2+; HTTPS only

---

### 4.3 SQL Database Backup & Recovery

#### **Automatic Backups**
- **Full**: Weekly
- **Differential**: Daily
- **Transaction logs**: Every ~5 minutes
- **PITR** (Point-In-Time Restore): Restore to any point within retention (7-35 days default)

#### **Long-Term Retention (LTR)**
- Store backups in Blob Storage
- Retention: 1-10 years
- **Cost**: Separate blob storage charges

#### **Geo-Backup**
- **Automatic**: Async replicated to paired region
- Accessible if primary region fails
- **No additional cost** (included in GRS/GZRS)

---

### 4.4 Cosmos DB Backup

#### **Automatic Backup**
- Continuous backup mode (point-in-time restore to any time)
- Periodic mode: 4-hourly intervals
- **Backup storage**: Separate charge (in addition to RU/s)

#### **Restore Options**
- Restore to same account (replace data)
- Restore to new account (recovery)

---

## 📝 Quick Reference Tables

### **Relational Database Choice Decision**

| Scenario | Best Choice | Why |
|----------|------------|-----|
| Cloud-native SaaS | **SQL Database** | Fully managed, auto-scaling, latest features |
| Lift-and-shift SQL Server | **SQL Managed Instance** | Full compatibility, minimal code changes |
| Massive scale (>4 TB) | **SQL Database Hyperscale** | Auto-scale storage, rapid backups |
| Open-source DB | **Azure Database for PostgreSQL/MySQL** | Managed open-source, cost-effective |
| Global distribution, multi-region writes | **Cosmos DB** | <10ms latency, active-active replication |
| Custom SQL Server features | **SQL Server on VM** | Full control, but highest ops burden |

---

### **Storage Type Decision**

| Data Type | Best Choice | When |
|-----------|------------|------|
| Unstructured files | **Blob Storage** | Media, backups, archives |
| Big Data / Analytics | **Data Lake Gen2** | Spark, Hadoop, structured folders |
| Key-value store | **Table Storage** OR **Cosmos DB Table API** | Simple schema, massive scale |
| JSON documents | **Cosmos DB NoSQL** | Global, variable schema, high throughput |
| Structured analytics | **Azure Synapse SQL** | Data warehouse, complex queries |
| Real-time processing | **Cosmos DB + Spark** | IoT, personalization, real-time |

---

### **Redundancy Decision**

| Requirement | Choose | Cost | Protection |
|------------|--------|------|-----------|
| Local HA, cost-optimized | **LRS or ZRS** | $ | Zone/DC failure |
| Disaster recovery needed | **GRS or GZRS** | $$$ | Regional outage |
| Read access during DR | **RA-GRS or RA-GZRS** | $$$$ | Regional outage + read access |
| Maximum protection | **GZRS** | $$$ | Zone failure + regional outage |

---

## 🎯 Exam Tips

### **Relational Data**
1. **DTU vs vCore**: DTU = bundled simple; vCore = granular flexible
2. **Elastic pools** = cost savings when databases have **variable peak times**
3. **Hyperscale tier** = solution for >4 TB databases needing fast scaling
4. **Geo-replication** secondary = **read-only until failover**
5. **SQL Managed Instance** = full SQL Server compatibility; higher latency than SQL DB
6. **PITR** = automatic; configurable retention; restore to specific point in time

### **Semi-Structured / Unstructured**
1. **Blob Storage tiers**: Hot > Cool (30d) > Cold (90d) > Archive (180d)
2. **ADLS Gen2** = hierarchical namespace; better for big data than Blob
3. **Cosmos DB RUs**: Global replication = RUs × regions; consistency affects read cost
4. **RA-GRS**: Read during primary outage (secondary is read-only)
5. **Soft delete**: Recover deleted blobs/containers within retention

### **Data Integration**
1. **Data Factory** = code-free ETL/ELT orchestration
2. **PolyBase / COPY** = fastest way to load data into Synapse
3. **Self-hosted IR** = required for on-premises data sources
4. **ELT pattern** = scales better than ETL for massive data (transform in cloud)
5. **Synapse SQL Pool** = MPP for structured analytics

### **Protection & Redundancy**
1. **LRS + GRS RPO** ≤ 15 minutes (potential data loss window)
2. **GZRS** = best overall (zone-resilient + geo-redundant)
3. **ARM locks** = prevent account deletion, not resource deletion
4. **Soft delete** = recovery within retention; must enable proactively
5. **Immutability** = WORM compliance; blocks any modification/deletion

---

## 📚 Additional Study Resources

### **Official Microsoft Learn Paths**
- [AZ-305: Design data storage solutions](https://learn.microsoft.com/en-us/training/paths/design-data-storage-solutions/)
- [Design relational data storage](https://learn.microsoft.com/en-us/training/modules/design-data-storage-solution-for-relational-data/)
- [Design non-relational data storage](https://learn.microsoft.com/en-us/training/modules/design-data-storage-solution-for-non-relational-data/)
- [Design data integration](https://learn.microsoft.com/en-us/training/modules/design-data-integration/)

### **Key Documentation**
- [Azure SQL Database - vCore pricing model](https://learn.microsoft.com/en-us/azure/azure-sql/database/service-tiers-sql-database-vcore/)
- [Azure Cosmos DB - Request Units](https://learn.microsoft.com/en-us/azure/cosmos-db/request-units)
- [Azure Blob Storage - Access Tiers](https://learn.microsoft.com/en-us/azure/storage/blobs/access-tiers-overview)
- [Azure Storage Redundancy](https://learn.microsoft.com/en-us/azure/storage/common/storage-redundancy)
- [Azure Data Factory - Copy Activity](https://learn.microsoft.com/en-us/azure/data-factory/copy-activity-overview)
- [Azure Synapse Analytics - PolyBase](https://learn.microsoft.com/en-us/azure/synapse-analytics/sql-data-warehouse/load-data-widgetized)

---

**Last Updated:** January 2026  
**Based on:** AZ-305 Exam Skills Measured (October 18, 2024 version)

## Practice Questions

1. A SaaS platform hosts hundreds of small tenant databases with spiky but non-overlapping peaks. The team wants predictable spend while keeping performance acceptable. Which choice fits best?
  - A. Single Azure SQL Database per tenant on vCore Business Critical
  - B. Azure SQL Database elastic pool sized to shared eDTUs/vCores
  - C. SQL Managed Instance with all tenants in one database
  - D. SQL Server on VM with manual scaling
2. A new relational workload will grow toward 80-100 TB and needs fast scale-out reads with minimal management. Which option aligns best?
  - A. SQL Database Business Critical
  - B. SQL Database Hyperscale
  - C. SQL Managed Instance General Purpose
  - D. SQL Server on VM with Always On
3. You must lift and shift a SQL Server instance that uses SQL Agent jobs and linked servers, with minimal code change. Which service should you choose?
  - A. Azure SQL Database
  - B. Azure SQL Managed Instance
  - C. Azure Cosmos DB
  - D. Azure Database for PostgreSQL
4. A storage account must remain readable during a regional outage without performing a failover. Which redundancy option meets this requirement?
  - A. LRS
  - B. ZRS
  - C. GRS
  - D. RA-GZRS
5. You need to move 50 TB from on-premises Oracle into Synapse for analytics. The pipeline must be hybrid-capable and perform high-throughput loads. What is the best pattern?
  - A. Data Factory with self-hosted IR → stage to ADLS Gen2 → PolyBase/COPY into dedicated SQL pool
  - B. Direct connect Power BI to Oracle
  - C. Manually SFTP files to Blob, then import with bcp
  - D. Use Cosmos DB change feed to Synapse
6. Compliance logs must be retained for seven years, rarely accessed, and protected from tampering. What combination is most appropriate?
  - A. Hot tier with soft delete
  - B. Cool tier with snapshots
  - C. Archive tier with immutability policy
  - D. ZRS Hot tier only
7. A global app needs sub-10 ms writes and reads in multiple Azure regions with active-active capability. Which service fits best?
  - A. Azure SQL Database with geo-replication
  - B. Azure Cosmos DB with multi-region writes
  - C. Azure Database for PostgreSQL
  - D. SQL Server on VM with log shipping
8. An IoT workload stores JSON sensor data, needs hierarchical folders, and must integrate easily with Spark. Which storage choice is best?
  - A. Azure Blob Storage (flat namespace)
  - B. Azure Data Lake Storage Gen2
  - C. Azure Table Storage
  - D. Azure SQL Database
9. Analysts need ad-hoc SQL over files in a data lake without provisioning compute up front. Which option should you choose?
  - A. Synapse dedicated SQL pool
  - B. Synapse serverless SQL pool
  - C. SQL Database serverless tier
  - D. SQL Managed Instance
10. A team needs automatic backups with point-in-time restore for the last 14 days on a managed relational service with minimal ops. What should they use?
   - A. Azure SQL Database with default PITR retention
   - B. SQL Server on VM with manual jobs
   - C. Cosmos DB periodic backup
   - D. Azure Table Storage with snapshots

## Answer Key

- 1: B
- 2: B
- 3: B
- 4: D
- 5: A
- 6: C
- 7: B
- 8: B
- 9: B
- 10: A
