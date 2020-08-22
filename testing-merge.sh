

git checkout testing
git pull

git merge develop
git push origin testing
echo '************************************合并后的testing版本号:*************************************'
git rev-parse HEAD
git checkout develop

