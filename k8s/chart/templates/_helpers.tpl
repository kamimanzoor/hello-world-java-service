{{/* vim: set filetype=mustache: */}}

{{/*
Create a subdomain value that varies for the environments.
Lower envs contain subdomain as env name
Prod is without env specific subdomain
*/}}
{{- define "env-subdomain" -}}
{{- if ne .Values.deploy.env "prod" -}}
{{- printf "%s." .Values.deploy.env -}}
{{- end -}}
{{- end -}}