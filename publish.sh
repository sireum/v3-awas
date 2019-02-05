#!/usr/bin/env bash
rm -fR target
mkdir target
cd target
git clone git@github.com:sireum/v3-awas-website.git
shopt -s extglob
rm -fR v3-awas-website/!(sireum-v3-VER|sireum-v3-dev-VER)
shopt -u extglob
cd ../src/main/rst
make clean
touch source/index.rst
make html
cp -R build/html/* ../../../target/v3-awas-website/
cd ../../../target/v3-awas-website
echo "awas.sireum.org" > CNAME
rm .git/index
git reset
git add --all
git commit -m 'Updated.'
git push
