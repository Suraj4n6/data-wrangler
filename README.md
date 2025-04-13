# Enhanced Wrangler: Byte Size and Time Duration Support

This project is a fork of [CDAP Wrangler](https://github.com/data-integrations/wrangler) enhanced with support for parsing and aggregating **byte size** and **time duration** units within data transformation recipes.

> 📌 This enhancement was developed as part of a Software Engineer Intern assignment to improve usability for data size and timing operations directly in Wrangler.

---

## ✨ New Features

### ✅ Byte Size Unit Parser
Parses values like:
- `10KB`
- `1.5MB`
- `2GB`

➡️ Canonically stored and processed in **bytes**.

### ✅ Time Duration Unit Parser
Parses values like:
- `250ms`
- `1.2s`
- `5m`

➡️ Canonically stored and processed in **nanoseconds**.

---

## 🧮 New Directive: `aggregate-stats`

### 📌 Usage

```wrangler
aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec
