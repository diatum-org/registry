set -e

rm -rf backup
mkdir -p backup
mysqldump -u root -proot registry > backup/registry.sql
rm -f backup.tgz
tar -czvf backup.tgz backup

