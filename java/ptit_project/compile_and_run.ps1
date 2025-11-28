# Compile and run helper for Windows PowerShell
param(
    [string[]]$Paths = @('sample_texts')
)

# Compile
javac -d out src\*.java
if ($LASTEXITCODE -ne 0) { Write-Error "Compilation failed"; exit 1 }

# Build args: allow -k=N and file/dir list
$argsList = @()
foreach ($p in $Paths) { $argsList += $p }

java -cp out Main $argsList
